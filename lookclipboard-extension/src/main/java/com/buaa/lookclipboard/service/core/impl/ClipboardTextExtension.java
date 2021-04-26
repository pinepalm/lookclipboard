/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 21:09:04
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 22:14:13
 * @Description: 剪贴板文本扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.appmodel.core.datatransfer.Clipboard;
import com.buaa.lookclipboard.domain.IRecord;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板文本扩展
 */
public final class ClipboardTextExtension extends ClipboardExtension<String> {
    @Override
    protected IActionResult onReceivedInternal(IRecord record, String content) {
        return new ActionResult(content, 200);
    }

    @Override
    public DataFormat getDataFormat() {
        return DataFormat.PLAIN_TEXT;
    }

    @Override
    public IActionResult onCopied(IRecord record) {
        ClipboardContent content = new ClipboardContent();
        content.putString(record.getInfo());
        return new ActionResult(null, Clipboard.setContent(content) ? 200 : 400);
    }

    @Override
    public IActionResult onEdited(IRecord record, Object editInfo) {
        return new ActionResult(editInfo.toString(), 200);
    }

    @Override
    public IActionResult onDeleted(IRecord record) {
        return new ActionResult(null, 200);
    }
}
