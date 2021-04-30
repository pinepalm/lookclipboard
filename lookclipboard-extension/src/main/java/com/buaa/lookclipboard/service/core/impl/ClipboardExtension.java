/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 20:42:26
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 20:19:31
 * @Description: 剪贴板扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;
import com.buaa.lookclipboard.model.IActionResult;
import com.buaa.lookclipboard.service.core.IClipboardExtension;

/**
 * 剪贴板扩展
 * 
 * @param <T> 内容类型
 */
@SuppressWarnings("unchecked")
public abstract class ClipboardExtension<T> implements IClipboardExtension {
    protected abstract boolean isEqualInternal(IRecord lastRecord, T content);
    protected abstract IActionResult onReceivedInternal(IRecord newRecord, T content, Ref<String> outContent);

    @Override
    public boolean isEqual(IRecord lastRecord, Object content) {
        return isEqualInternal(lastRecord, (T) content);
    }

    @Override
    public IActionResult onReceived(IRecord newRecord, Object content, Ref<String> outContent) {
        return onReceivedInternal(newRecord, (T) content, outContent);
    }
}
