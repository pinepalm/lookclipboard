/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 20:35:05
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-14 21:51:07
 * 
 * @Description: 剪贴板服务
 */
package com.buaa.lookclipboard.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

import com.buaa.appmodel.core.datatransfer.Clipboard;
import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.foundation.Ref;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import com.buaa.lookclipboard.service.IClipboardService;
import com.buaa.lookclipboard.service.core.IClipboardExtension;
import com.buaa.lookclipboard.util.ExtensionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.FileUtils;
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
                e.printStackTrace();
                if (context != null) {
                    context.setResult(ActionResultCode.JSON_PARSE_ERROR.asResult(null));
                }
            }, JsonProcessingException.class),

            new ExceptionHandler<SQLException, ActionResultTryContext>((e, context) -> {
                e.printStackTrace();
                if (context != null) {
                    context.setResult(ActionResultCode.INTERNAL_SQL_ERROR.asResult(null));
                }
            }, SQLException.class),

            new ExceptionHandler<Exception, ActionResultTryContext>((e, context) -> {
                e.printStackTrace();
                if (context != null) {
                    context.setResult(ActionResultCode.INTERNAL_ERROR.asResult(null));
                }
            }, Exception.class)};

            
    private final Map<DataFormat, IClipboardExtension> extensions = new LinkedHashMap<>();
    private final Object syncRoot = new Object();

    private boolean isCopying;
    private Record lastRecord;

    private ClipboardService() {
        ServiceLoader<IClipboardExtension> serviceLoader = ExtensionUtil.createServiceLoader(
                Arrays.asList(getClass().getResource("/extensions").getPath()),
                IClipboardExtension.class);
        for (IClipboardExtension extension : serviceLoader) {
            extensions.put(extension.getDataFormat(), extension);
        }

        Clipboard.contentChanged.addEventHandler((sender, args) -> receive());
    }

    private void receive() {
        if (isCopying) {
            isCopying = false;
            return;
        }

        for (IClipboardExtension extension : extensions.values()) {
            DataFormat dataFormat = extension.getDataFormat();
            Object content = Clipboard.getContent(dataFormat);

            if (content != null) {
                synchronized (syncRoot) {
                    if (lastRecord == null
                            || !Objects.equals(dataFormat, lastRecord.getDataFormat())
                            || !extension.isEqual(lastRecord.asReadOnly(), content)) {
                        TryWrapper<ActionResultTryContext> addRecord =
                                new TryWrapper<>((context) -> {
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
                                        App.runJavaScript(addRecordJS);
                                    }
                                }, exceptionHandlers);

                        addRecord.invoke();
                    }
                }

                break;
            }
        }
    }

    private IActionResult handle(String id,
            ThrowingBiFunction<Record, IClipboardExtension, IActionResult, Exception> function) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper<ActionResultTryContext> handle = new TryWrapper<>((context) -> {
            Record record = RecordDao.getInstance().getById(id);
            context.setResult(
                    record != null 
                    ? handle(record, function) 
                    : ActionResultCode.RECORD_NOT_EXISTS.asResult(null));
        }, exceptionHandlers);

        handle.invoke(resultContext);
        return resultContext.getResult();
    }

    private IActionResult handle(Record record,
            ThrowingBiFunction<Record, IClipboardExtension, IActionResult, Exception> function) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper<ActionResultTryContext> handle = new TryWrapper<>((context) -> {
            IClipboardExtension extension = extensions.get(record.getDataFormat());
            context.setResult(
                    extension != null 
                    ? function.apply(record, extension) 
                    : ActionResultCode.RECORD_EXTENSION_NOT_EXISTS.asResult(null));
        }, exceptionHandlers);

        handle.invoke(resultContext);
        return resultContext.getResult();
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

        TryWrapper<ActionResultTryContext> deleteBatch = new TryWrapper<>((context) -> {
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
        }, exceptionHandlers);

        deleteBatch.invoke(resultContext);
        return JsonUtil.stringify(resultContext.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteAll() {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper<ActionResultTryContext> deleteAll = new TryWrapper<>((context) -> {
            FileUtils.cleanDirectory(AppConfig.getInstance().getAppDataFolder());
            RecordDao.getInstance().deleteAll();
            context.setResult(ActionResultCode.SUCCESS.asResult(null));
        }, exceptionHandlers);

        deleteAll.invoke(resultContext);
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

        TryWrapper<ActionResultTryContext> setIsPinned = new TryWrapper<>((context) -> {
            Record record = RecordDao.getInstance().getById(id);
            if (record != null) {
                record.setIsPinned(isPinned);
                RecordDao.getInstance().update(record);
                context.setResult(ActionResultCode.SUCCESS.asResult(null));
            } else {
                context.setResult(ActionResultCode.RECORD_NOT_EXISTS.asResult(null));
            }
        }, exceptionHandlers);

        setIsPinned.invoke(resultContext);
        return JsonUtil.stringify(resultContext.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRecordsByCondition(String conditionJSON) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper<ActionResultTryContext> getRecordsByCondition = new TryWrapper<>((context) -> {
            RecordQueryCondition condition = JsonUtil.parse(conditionJSON, RecordQueryCondition.class, true);
            List<Record> recordList = RecordDao.getInstance().getByCondition(condition);
            context.setResult(ActionResultCode.SUCCESS.asResult(recordList));
        }, exceptionHandlers);

        getRecordsByCondition.invoke(resultContext);
        return JsonUtil.stringify(resultContext.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRecordsCountByCondition(String conditionJSON) {
        ActionResultTryContext resultContext = new ActionResultTryContext();

        TryWrapper<ActionResultTryContext> getRecordsCountByCondition = new TryWrapper<>((context) -> {
            RecordQueryCondition condition = JsonUtil.parse(conditionJSON, RecordQueryCondition.class, true);
            Integer recordsCount = RecordDao.getInstance().getCountByCondition(condition);
            context.setResult(ActionResultCode.SUCCESS.asResult(recordsCount));
        }, exceptionHandlers);

        getRecordsCountByCondition.invoke(resultContext);
        return JsonUtil.stringify(resultContext.getResult());
    }
}
