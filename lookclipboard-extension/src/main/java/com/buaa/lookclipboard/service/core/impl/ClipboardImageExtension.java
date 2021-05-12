/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 21:11:11
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-12 16:40:53
 * 
 * @Description: 剪贴板图像扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import java.io.File;
import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.AppConfig;
import com.buaa.lookclipboard.model.IRecord;

import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板图像扩展
 */
public final class ClipboardImageExtension extends ClipboardExtension<Image> {
    private final static String IMAGE_DATA = "imagedata";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEqualInternal(IRecord lastRecord, Image content) {
        File appDataFolder = AppConfig.getInstance().getAppDataFolder();
        if (appDataFolder == null) {
            return false;
        }

        return false; // 待完善...
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReceivedInternal(IRecord newRecord, Image content, Ref<String> outContent)
            throws Exception {
        outContent.set(Double.toString(content.getHeight()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFormat getDataFormat() {
        return DataFormat.IMAGE;
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
        outContent.set(record.getContent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleted(IRecord record) throws Exception {

    }
}
