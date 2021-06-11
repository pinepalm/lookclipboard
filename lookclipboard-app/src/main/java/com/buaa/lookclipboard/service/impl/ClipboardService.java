/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 20:35:05
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-11 15:04:20
 * 
 * @Description: 剪贴板服务
 */
package com.buaa.lookclipboard.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import com.buaa.appmodel.core.datatransfer.Clipboard;
import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.foundation.Ref;
import com.buaa.commons.foundation.ServiceLoaderEx;
import com.buaa.commons.foundation.function.ThrowingBiFunction;
import com.buaa.commons.lang.ExceptionHandler;
import com.buaa.commons.lang.TryWrapper;
import com.buaa.commons.util.JsonUtil;
import com.buaa.commons.util.StringUtil;
import com.buaa.lookclipboard.App;
import com.buaa.lookclipboard.AppConfig;
import com.buaa.lookclipboard.dao.impl.RecordDao;
import com.buaa.lookclipboard.model.IActionResult;
import com.buaa.lookclipboard.model.Record;
import com.buaa.lookclipboard.model.RecordQueryCondition;
import com.buaa.lookclipboard.model.ActionResultCode;
import com.buaa.lookclipboard.model.ActionResultTryContext;
import javafx.application.Platform;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import com.buaa.lookclipboard.service.IClipboardService;
import com.buaa.lookclipboard.service.core.IClipboardExtension;
import com.buaa.lookclipboard.util.ExtensionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.StringEscapeUtils;

/**
 * 剪贴板服务
 */
public final class ClipboardService implements IClipboardService {
    private final static String ADD_RECORD = "addRecord";

    private final static Lazy<ClipboardService> instance = new Lazy<>(() -> new ClipboardService());

    /**
     * 获取剪贴板服务实例
     * 
     * @return 剪贴板服务实例
     */
    public static ClipboardService getInstance() {
        return instance.getValue();
    }

    private final ExceptionHandler<?, ?>[] exceptionHandlers = new ExceptionHandler<?, ?>[] {
            new ExceptionHandler<JsonProcessingException, ActionResultTryContext>((e, context) -> {
                ActionResultCode code = ActionResultCode.JSON_PARSE_ERROR;
                LogService.getInstance().error(code.getMessage(), e);
                if (context != null) {
                    context.setResult(code.asResult(null));
                }
            }, JsonProcessingException.class),

            new ExceptionHandler<SQLException, ActionResultTryContext>((e, context) -> {
                ActionResultCode code = ActionResultCode.INTERNAL_SQL_ERROR;
                LogService.getInstance().error(code.getMessage(), e);
                if (context != null) {
                    context.setResult(code.asResult(null));
                }
            }, SQLException.class),

            new ExceptionHandler<Exception, ActionResultTryContext>((e, context) -> {
                ActionResultCode code = ActionResultCode.INTERNAL_ERROR;
                LogService.getInstance().error(code.getMessage(), e);
                if (context != null) {
                    context.setResult(code.asResult(null));
                }
            }, Exception.class)};

            
    private final Map<DataFormat, IClipboardExtension> extensions = new LinkedHashMap<>();
    private final Object syncRoot = new Object();

    private boolean monitored = true;
    private boolean isCopying = false;
    private Record lastRecord = null;

    private ClipboardService() {
        try {
            String extensionsPath = FileUtils.getFile(".", "extensions").toURI().toURL().getPath();
            ServiceLoaderEx<IClipboardExtension> serviceLoader = ExtensionUtil.createServiceLoader(Arrays.asList(extensionsPath), IClipboardExtension.class, () -> {
                Vector<URL> urls = new Vector<>();
                try {
                    urls.add(FileUtils.getFile(".", "conf", "services", IClipboardExtension.class.getName()).toURI().toURL());
                } catch (MalformedURLException e) {
                    ExceptionUtils.rethrow(e);
                }
                return urls.elements();
            });
            for (IClipboardExtension extension : serviceLoader) {
                extensions.put(extension.getDataFormat(), extension);
            }
        } catch (Exception e) {
            LogService.getInstance().error("extensions load failed", e);
        }

        Clipboard.contentChanged.addEventHandler((sender, args) -> receive());
    }

    private void receive() {
        if (isCopying) {
            isCopying = false;
            return;
        }

        if (isMonitored()) {
            for (IClipboardExtension extension : extensions.values()) {
                DataFormat dataFormat = extension.getDataFormat();
                Object content = Clipboard.getContent(dataFormat);
    
                if (content != null) {
                    CompletableFuture.runAsync(() -> {
                        synchronized (syncRoot) {
                            if (lastRecord == null
                                    || !Objects.equals(dataFormat, lastRecord.getDataFormat())
                                    || !extension.isEqual(lastRecord.asReadOnly(), content)) {
                                TryWrapper.<ActionResultTryContext>builder()
                                        .tryStatement((context) -> {
                                            Record record = Record.createNow(dataFormat);
                                            Ref<String> outContent = new Ref<>();
                                            extension.onReceived(record.asReadOnly(), content, outContent);

                                            record.setContent(outContent.get());
                                            lastRecord = record;
                                            RecordDao.getInstance().add(record);

                                            String recordJson = JsonUtil.stringify(record);
                                            if (recordJson != null) {
                                                String addRecordJS = StringUtil.interpolate(
                                                        "${ADD_RECORD}(\"${recordJson}\")", new Object[][] 
                                                        {
                                                            {"ADD_RECORD", ADD_RECORD}, 
                                                            {"recordJson", StringEscapeUtils.escapeJson(recordJson)}
                                                        });
                                                Platform.runLater(() -> App.runJavaScript(addRecordJS));
                                            }
                                        })
                                        .catchStatement(exceptionHandlers)
                                        .build()
                                        .invoke();
                            }
                        }
                    });
    
                    break;
                }
            }
        }
    }

    private IActionResult handle(String id,
            ThrowingBiFunction<Record, IClipboardExtension, IActionResult, Exception> function) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper.<ActionResultTryContext>builder()
                .tryStatement((context) -> {
                    Record record = RecordDao.getInstance().getById(id);
                    context.setResult(
                            record != null 
                            ? handle(record, function) 
                            : ActionResultCode.RECORD_NOT_EXISTS.asResult(null));
                })
                .catchStatement(exceptionHandlers)
                .build()
                .invoke(resultContext);

        return resultContext.getResult();
    }

    private IActionResult handle(Record record,
            ThrowingBiFunction<Record, IClipboardExtension, IActionResult, Exception> function) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper.<ActionResultTryContext>builder()
                .tryStatement((context) -> {
                    IClipboardExtension extension = extensions.get(record.getDataFormat());
                    context.setResult(
                            extension != null 
                            ? function.apply(record, extension) 
                            : ActionResultCode.RECORD_EXTENSION_NOT_EXISTS.asResult(null));
                })
                .catchStatement(exceptionHandlers)
                .build()
                .invoke(resultContext);

        return resultContext.getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMonitored() {
        return monitored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String copy(String id) {
        IActionResult result = handle(id, (record, extension) -> {
            try {
                isCopying = true;
                Ref<ClipboardContent> outContent = new Ref<>();
                extension.onCopied(record.asReadOnly(), outContent);
                Clipboard.setContent(outContent.get());

                return ActionResultCode.SUCCESS.asResult(null);
            } catch (Exception e) {
                isCopying = false;
                throw e;
            }
        });

        return JsonUtil.stringify(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete(String id) {
        IActionResult result = handle(id, (record, extension) -> {
            extension.onDeleted(record.asReadOnly());
            RecordDao.getInstance().delete(record.getID());

            return ActionResultCode.SUCCESS.asResult(null);
        });

        return JsonUtil.stringify(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteBatch(String idsJSON) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper.<ActionResultTryContext>builder()
                .tryStatement((context) -> {
                    String[] ids = JsonUtil.parse(idsJSON, String[].class);
                    List<Record> recordList = RecordDao.getInstance().getByIdList(ids);
                    List<IActionResult> results = new ArrayList<>();
                    for (Record record : recordList) {
                        results.add(handle(record, (rec, extension) -> {
                            extension.onDeleted(rec.asReadOnly());

                            return ActionResultCode.SUCCESS.asResult(null);
                        }));
                    }
                    RecordDao.getInstance().deleteBatch(ids);
                    context.setResult(ActionResultCode.SUCCESS.asResult(results));
                })
                .catchStatement(exceptionHandlers)
                .build()
                .invoke(resultContext);

        return JsonUtil.stringify(resultContext.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteAll() {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper.<ActionResultTryContext>builder()
                .tryStatement((context) -> {
                    FileUtils.cleanDirectory(AppConfig.getInstance().getAppDataFolder());
                    RecordDao.getInstance().deleteAll();
                    context.setResult(ActionResultCode.SUCCESS.asResult(null));
                })
                .catchStatement(exceptionHandlers)
                .build()
                .invoke(resultContext);

        return JsonUtil.stringify(resultContext.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit(String id, Object editContent) {
        IActionResult result = handle(id, (record, extension) -> {
            Ref<String> outContent = new Ref<>();
            extension.onEdited(record.asReadOnly(), editContent, outContent);
            record.setContent(outContent.get());
            RecordDao.getInstance().update(record);

            return ActionResultCode.SUCCESS.asResult(null);
        });

        return JsonUtil.stringify(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String setIsPinned(String id, boolean isPinned) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper.<ActionResultTryContext>builder()
                .tryStatement((context) -> {
                    Record record = RecordDao.getInstance().getById(id);
                    if (record != null) {
                        record.setIsPinned(isPinned);
                        RecordDao.getInstance().update(record);
                        context.setResult(ActionResultCode.SUCCESS.asResult(null));
                    } else {
                        context.setResult(ActionResultCode.RECORD_NOT_EXISTS.asResult(null));
                    }
                })
                .catchStatement(exceptionHandlers)
                .build()
                .invoke(resultContext);

        return JsonUtil.stringify(resultContext.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRecordsByCondition(String conditionJSON) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper.<ActionResultTryContext>builder()
                .tryStatement((context) -> {
                    RecordQueryCondition condition = JsonUtil.parse(conditionJSON, RecordQueryCondition.class, true);
                    List<Record> recordList = RecordDao.getInstance().getByCondition(condition);
                    context.setResult(ActionResultCode.SUCCESS.asResult(recordList));
                })
                .catchStatement(exceptionHandlers)
                .build()
                .invoke(resultContext);

        return JsonUtil.stringify(resultContext.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRecordsCountByCondition(String conditionJSON) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper.<ActionResultTryContext>builder()
                .tryStatement((context) -> {
                    RecordQueryCondition condition = JsonUtil.parse(conditionJSON, RecordQueryCondition.class, true);
                    Integer recordsCount = RecordDao.getInstance().getCountByCondition(condition);
                    context.setResult(ActionResultCode.SUCCESS.asResult(recordsCount));
                })
                .catchStatement(exceptionHandlers)
                .build()
                .invoke(resultContext);

        return JsonUtil.stringify(resultContext.getResult());
    }
}
