/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 20:35:05
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 22:55:06
 * @Description: 剪贴板服务
 */
package com.buaa.lookclipboard.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.BiFunction;

import com.buaa.appmodel.core.datatransfer.Clipboard;
import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.App;
import com.buaa.lookclipboard.dao.impl.RecordDao;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;
import com.buaa.lookclipboard.model.Record;
import com.buaa.lookclipboard.model.RecordQueryCondition;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import com.buaa.lookclipboard.service.IClipboardService;
import com.buaa.lookclipboard.service.core.IClipboardExtension;
import com.buaa.lookclipboard.util.ActionResultUtil;
import com.buaa.lookclipboard.util.ExtensionUtil;
import com.buaa.lookclipboard.util.JsonUtil;
import com.buaa.lookclipboard.util.ObjectUtil;

/**
 * 剪贴板服务
 */
public final class ClipboardService implements IClipboardService {
    private final static Lazy<ClipboardService> instance = new Lazy<>(() -> new ClipboardService());

    /**
     * 获取剪贴板服务实例
     * 
     * @return 剪贴板服务实例
     */
    public static ClipboardService getInstance() {
        return instance.getValue();
    }

    private final List<IClipboardExtension> extensions = new ArrayList<>();

    private boolean isCopying;
    private Record lastRecord;

    private ClipboardService() {
        ServiceLoader<IClipboardExtension> serviceLoader = ExtensionUtil.createServiceLoader(
                Arrays.asList(getClass().getResource("/extensions").getPath()), IClipboardExtension.class);
        for (IClipboardExtension extension : serviceLoader) {
            extensions.add(extension);
        }

        Clipboard.contentChanged.addEventHandler((sender, args) -> receive());
    }

    private void receive() {
        if (isCopying) {
            isCopying = false;
            return;
        }

        for (IClipboardExtension extension : extensions) {
            DataFormat dataFormat = extension.getDataFormat();
            Object content = Clipboard.getContent(dataFormat);

            if (content != null) {
                if (lastRecord == null || !dataFormat.equals(lastRecord.getDataFormat())
                        || !extension.isEqual(lastRecord.asReadOnly(), content)) {
                    Record record = Record.createNow(dataFormat);
                    Ref<String> outContent = new Ref<>();
                    IActionResult result = extension.onReceived(record.asReadOnly(), content, outContent);

                    if (ActionResultUtil.isSuccess(result)) {
                        record.setContent(outContent.get());
                        lastRecord = record;
                        RecordDao.getInstance().add(record);

                        String recordJson = JsonUtil.stringify(record);
                        if (recordJson != null) {
                            String addRecord = String.format("addRecord(%s)",
                                    ObjectUtil.asJavaScriptString(recordJson));
                            App.runJavaScript(addRecord);
                        }
                    }
                }

                break;
            }
        }
    }

    private String handle(String id, BiFunction<Record, IClipboardExtension, String> function) {
        Record record = RecordDao.getInstance().getById(id);
        if (record != null) {
            for (IClipboardExtension extension : extensions) {
                if (record.getDataFormat().equals(extension.getDataFormat())) {
                    return function.apply(record, extension);
                }
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String copy(String id) {
        return handle(id, (record, extension) -> {
            isCopying = true;

            Ref<ClipboardContent> outContent = new Ref<>();
            IActionResult result = extension.onCopied(record.asReadOnly(), outContent);

            if (ActionResultUtil.isSuccess(result)) {
                Clipboard.setContent(outContent.get());
            } else {
                isCopying = false;
            }

            return JsonUtil.stringify(result);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete(String id) {
        return handle(id, (record, extension) -> {
            IActionResult result = extension.onDeleted(record.asReadOnly());

            if (ActionResultUtil.isSuccess(result)) {
                RecordDao.getInstance().delete(record.getID());
            }

            return JsonUtil.stringify(result);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit(String id, Object editContent) {
        return handle(id, (record, extension) -> {
            Ref<String> outContent = new Ref<>();
            IActionResult result = extension.onEdited(record.asReadOnly(), editContent, outContent);

            if (ActionResultUtil.isSuccess(result)) {
                record.setContent(outContent.get());
                RecordDao.getInstance().update(record);
            }

            return JsonUtil.stringify(result);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String setIsPinned(String id, boolean isPinned) {
        Record record = RecordDao.getInstance().getById(id);

        if (record != null) {
            record.setIsPinned(isPinned);
            RecordDao.getInstance().update(record);
        }

        return JsonUtil.stringify(new ActionResult(null, 200));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRecordsByCondition(String conditionJSON) {
        RecordQueryCondition condition = JsonUtil.parse(conditionJSON, RecordQueryCondition.class);
        List<Record> recordList = condition != null ? RecordDao.getInstance().getByCondition(condition) : null;
        return JsonUtil.stringify(recordList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRecordsCountByCondition(String conditionJSON) {
        RecordQueryCondition condition = JsonUtil.parse(conditionJSON, RecordQueryCondition.class);
        int recordsCount = condition != null ? RecordDao.getInstance().getCountByCondition(condition) : 0;
        return recordsCount;
    }
}