/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 21:13:30
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 22:08:21
 * @Description: 剪贴板文件扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import java.io.File;
import java.util.List;

import com.buaa.lookclipboard.domain.IRecord;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;

import javafx.scene.input.DataFormat;

/**
 * 剪贴板文件扩展
 */
public final class ClipboardFilesExtension extends ClipboardExtension<List<File>> {
    @Override
    protected IActionResult onReceivedInternal(IRecord record, List<File> content) {
        return new ActionResult(Integer.toString(content.size()), 200);
    }

    @Override
    public DataFormat getDataFormat() {
        return DataFormat.FILES;
    }

    @Override
    public IActionResult onCopied(IRecord record) {
        // TODO Auto-generated method stub
        return new ActionResult(null, 400);
    }

    @Override
    public IActionResult onEdited(IRecord record, Object editInfo) {
        // TODO Auto-generated method stub
        return new ActionResult(editInfo.toString(), 200);
    }

    @Override
    public IActionResult onDeleted(IRecord record) {
        return new ActionResult(null, 200);
    }
}
