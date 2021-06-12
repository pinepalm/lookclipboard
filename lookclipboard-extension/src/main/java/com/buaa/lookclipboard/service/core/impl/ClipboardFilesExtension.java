/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 21:13:30
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-12 22:48:21
 * 
 * @Description: 剪贴板文件扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.buaa.commons.foundation.Ref;
import com.buaa.commons.util.JsonUtil;
import com.buaa.lookclipboard.model.IRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板文件扩展
 */
public final class ClipboardFilesExtension extends ClipboardExtension<List<File>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needReceiveInternal(IRecord lastRecord, List<File> content) {
        if (CollectionUtils.isEmpty(content)) {
            return false;
        }

        Set<File> lastFiles = JsonUtil.parse(lastRecord.getContent(), new TypeReference<HashSet<File>>() {});
        if (lastFiles == null && content == null) {
            return false;
        }
        if (lastFiles == null || content == null) {
            return true;
        }

        Set<File> files = new HashSet<>(content);
        if (lastFiles.size() != files.size()) {
            return true;
        }

        return !files.containsAll(lastFiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReceivedInternal(IRecord newRecord, List<File> content, Ref<String> outContent) throws Exception {
        outContent.set(JsonUtil.stringify(content, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFormat getDataFormat() {
        return DataFormat.FILES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCopied(IRecord record, Ref<ClipboardContent> outContent) throws Exception {
        Set<File> files = JsonUtil.parse(record.getContent(), new TypeReference<HashSet<File>>() {
        }, true);
        List<File> copyFiles = 
                files != null
                ? files.stream().filter((file) -> file.exists()).collect(Collectors.toList())
                : null;
        ClipboardContent content = new ClipboardContent();
        content.putFiles(copyFiles);
        outContent.set(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEdited(IRecord record, Object editContent, Ref<String> outContent) throws Exception {
        outContent.set(JsonUtil.stringify(editContent, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleted(IRecord record) throws Exception {
        // 忽略...
    }
}
