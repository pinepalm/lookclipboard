/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 20:41:25
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 22:19:59
 * @Description: 剪贴板扩展接口
 */
package com.buaa.lookclipboard.service.core;

import com.buaa.lookclipboard.domain.IRecord;
import com.buaa.lookclipboard.model.IActionResult;

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
     * 在接收时调用
     * 
     * @param record  关联的记录
     * @param content 原始内容
     * @return 操作结果(若statusCode为200, 则info为处理后的信息, 否则info为错误信息)
     */
    IActionResult onReceived(IRecord record, Object content);

    /**
     * 在编辑时调用
     * 
     * @param record   关联的记录
     * @param editInfo 编辑信息
     * @return 操作结果(若statusCode为200, 则info为处理后的信息, 否则info为错误信息)
     */
    IActionResult onEdited(IRecord record, Object editInfo);

    /**
     * 在复制时调用
     * 
     * @param record 关联的记录
     * @return 操作结果(info为复制结果信息)
     */
    IActionResult onCopied(IRecord record);

    /**
     * 在删除时调用
     * 
     * @param record 关联的记录
     * @return 操作结果(info为删除结果信息)
     */
    IActionResult onDeleted(IRecord record);
}