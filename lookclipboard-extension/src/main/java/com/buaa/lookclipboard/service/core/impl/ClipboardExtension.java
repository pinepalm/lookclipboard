/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 20:42:26
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-06 17:15:38
 * 
 * @Description: 剪贴板扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;
import com.buaa.lookclipboard.service.core.IClipboardExtension;

/**
 * 剪贴板扩展
 * 
 * @param <T> 内容类型
 */
@SuppressWarnings("unchecked")
public abstract class ClipboardExtension<T> implements IClipboardExtension {
    /**
     * 内部判断是否内容与上一条记录相同
     * 
     * @param lastRecord 上一条记录
     * @param content    原始内容
     * @return 是否内容与上一条记录相同
     */
    protected abstract boolean isEqualInternal(IRecord lastRecord, T content);

    /**
     * 内部在接收时调用
     * 
     * @param newRecord  关联的记录
     * @param content    原始内容
     * @param outContent 处理后内容
     * @throws Exception
     */
    protected abstract void onReceivedInternal(IRecord newRecord, T content, Ref<String> outContent)
            throws Exception;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEqual(IRecord lastRecord, Object content) {
        return isEqualInternal(lastRecord, (T) content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReceived(IRecord newRecord, Object content, Ref<String> outContent)
            throws Exception {
        onReceivedInternal(newRecord, (T) content, outContent);
    }
}
