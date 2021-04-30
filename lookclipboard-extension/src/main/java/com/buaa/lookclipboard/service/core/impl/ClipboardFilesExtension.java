/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 21:13:30
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 20:21:41
 * @Description: 剪贴板文件扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import java.io.File;
import java.util.List;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板文件扩展
 */
public final class ClipboardFilesExtension extends ClipboardExtension<List<File>> {
    @Override
    public boolean isEqualInternal(IRecord lastRecord, List<File> content) {
        return false;
    }

    @Override
    protected IActionResult onReceivedInternal(IRecord newRecord, List<File> content, Ref<String> outContent) {
        outContent.set(Integer.toString(content.size()));
        return new ActionResult(null, 200);
    }

    @Override
    public DataFormat getDataFormat() {
        return DataFormat.FILES;
    }

    @Override
    public IActionResult onCopied(IRecord record, Ref<ClipboardContent> outContent) {
        return new ActionResult(null, 400);
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
