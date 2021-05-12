/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 21:13:30
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-06 17:18:55
 * 
 * @Description: 剪贴板文件扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import java.io.File;
import java.util.List;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;

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
    public boolean isEqualInternal(IRecord lastRecord, List<File> content) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReceivedInternal(IRecord newRecord, List<File> content, Ref<String> outContent)
            throws Exception {
        outContent.set(Integer.toString(content.size()));
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
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEdited(IRecord record, Object editContent, Ref<String> outContent)
            throws Exception {
        outContent.set(editContent.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleted(IRecord record) throws Exception {

    }
}
