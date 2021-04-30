/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 21:11:11
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 20:22:16
 * @Description: 剪贴板图像扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;

import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板图像扩展
 */
public final class ClipboardImageExtension extends ClipboardExtension<Image> {
    @Override
    public boolean isEqualInternal(IRecord lastRecord, Image content) {
        return false;
    }

    @Override
    protected IActionResult onReceivedInternal(IRecord newRecord, Image content, Ref<String> outContent) {
        outContent.set(Double.toString(content.getHeight()));
        
        return new ActionResult(null, 200);
    }

    @Override
    public DataFormat getDataFormat() {
        return DataFormat.IMAGE;
    }

    @Override
    public IActionResult onCopied(IRecord record, Ref<ClipboardContent> outContent) {
        return new ActionResult(null, 400);
    }

    @Override
    public IActionResult onEdited(IRecord record, Object editContent, Ref<String> outContent) {
        outContent.set(record.getContent());
        
        return new ActionResult(record.getContent(), 200);
    }

    @Override
    public IActionResult onDeleted(IRecord record) {
        return new ActionResult(null, 200);
    }
}
