/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 21:11:11
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 22:10:04
 * @Description: 剪贴板图像扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.lookclipboard.domain.IRecord;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;

import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板图像扩展
 */
public final class ClipboardImageExtension extends ClipboardExtension<Image> {
    @Override
    protected IActionResult onReceivedInternal(IRecord record, Image content) {
        return new ActionResult(Double.toString(content.getHeight()), 200);
    }

    @Override
    public DataFormat getDataFormat() {
        return DataFormat.IMAGE;
    }

    @Override
    public IActionResult onCopied(IRecord record) {
        // TODO Auto-generated method stub
        return new ActionResult(null, 400);
    }

    @Override
    public IActionResult onEdited(IRecord record, Object editInfo) {
        // TODO Auto-generated method stub
        return new ActionResult(record.getInfo(), 200);
    }

    @Override
    public IActionResult onDeleted(IRecord record) {
        return new ActionResult(null, 200);
    }
}
