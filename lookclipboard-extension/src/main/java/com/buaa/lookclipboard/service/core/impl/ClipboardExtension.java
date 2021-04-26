/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 20:42:26
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 21:57:28
 * @Description: 剪贴板扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.lookclipboard.domain.IRecord;
import com.buaa.lookclipboard.model.IActionResult;
import com.buaa.lookclipboard.service.core.IClipboardExtension;

/**
 * 剪贴板扩展
 * 
 * @param <T> 内容类型
 */
public abstract class ClipboardExtension<T> implements IClipboardExtension {
    protected abstract IActionResult onReceivedInternal(IRecord record, T content);

    @SuppressWarnings("unchecked")
    @Override
    public IActionResult onReceived(IRecord record, Object content) {
        return onReceivedInternal(record, (T) content);
    }
}
