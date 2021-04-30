/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 21:09:04
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 20:20:53
 * @Description: 剪贴板文本扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板文本扩展
 */
public final class ClipboardTextExtension extends ClipboardExtension<String> {
    @Override
    public boolean isEqualInternal(IRecord lastRecord, String content) {
        return lastRecord.getContent().equals(content);
    }

    @Override
    protected IActionResult onReceivedInternal(IRecord newRecord, String content, Ref<String> outContent) {
        outContent.set(content);
        
        return new ActionResult(null, 200);
    }

    @Override
    public DataFormat getDataFormat() {
        return DataFormat.PLAIN_TEXT;
    }

    @Override
    public IActionResult onCopied(IRecord record, Ref<ClipboardContent> outContent) {
        ClipboardContent content = new ClipboardContent();
        content.putString(record.getContent());
        outContent.set(content);

        return new ActionResult(null, 200);
    }

    @Override
    public IActionResult onEdited(IRecord record, Object editContent, Ref<String> outContent) {
        outContent.set(editContent.toString());

        return new ActionResult(null, 200);
    }

    @Override
    public IActionResult onDeleted(IRecord record) {
        return new ActionResult(null, 200);
    }
}
