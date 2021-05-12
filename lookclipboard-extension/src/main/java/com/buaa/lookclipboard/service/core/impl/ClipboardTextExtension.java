/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 21:09:04
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 12:01:39
 * 
 * @Description: 剪贴板文本扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板文本扩展
 */
public final class ClipboardTextExtension extends ClipboardExtension<String> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEqualInternal(IRecord lastRecord, String content) {
        return lastRecord.getContent().equals(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReceivedInternal(IRecord newRecord, String content, Ref<String> outContent)
            throws Exception {
        outContent.set(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFormat getDataFormat() {
        return DataFormat.PLAIN_TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCopied(IRecord record, Ref<ClipboardContent> outContent) throws Exception {
        ClipboardContent content = new ClipboardContent();
        content.putString(record.getContent());
        outContent.set(content);
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
