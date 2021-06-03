/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 20:41:25
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:48:04
 * 
 * @Description: 剪贴板扩展接口
 */
package com.buaa.lookclipboard.service.core;

import com.buaa.commons.foundation.Ref;
import com.buaa.lookclipboard.model.IRecord;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板扩展接口
 */
public interface IClipboardExtension {
    /**
     * 获取数据格式
     * 
     * @return 数据格式
     */
    DataFormat getDataFormat();

    /**
     * 判断是否内容与上一条记录相同
     * 
     * @param lastRecord 上一条记录
     * @param content    原始内容
     * @return 是否内容与上一条记录相同
     */
    boolean isEqual(IRecord lastRecord, Object content);

    /**
     * 在接收时调用
     * 
     * @param newRecord  关联的记录
     * @param content    原始内容
     * @param outContent 处理后内容
     * @throws Exception 可能抛出的异常
     */
    void onReceived(IRecord newRecord, Object content, Ref<String> outContent) throws Exception;

    /**
     * 在编辑时调用
     * 
     * @param record      关联的记录
     * @param editContent 编辑信息
     * @param outContent  处理后内容
     * @throws Exception 可能抛出的异常
     */
    void onEdited(IRecord record, Object editContent, Ref<String> outContent) throws Exception;

    /**
     * 在复制时调用
     * 
     * @param record     关联的记录
     * @param outContent 生成的数据包
     * @throws Exception 可能抛出的异常
     */
    void onCopied(IRecord record, Ref<ClipboardContent> outContent) throws Exception;

    /**
     * 在删除时调用
     * 
     * @param record 关联的记录
     * @throws Exception 可能抛出的异常
     */
    void onDeleted(IRecord record) throws Exception;
}
