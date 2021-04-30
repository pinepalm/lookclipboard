/*
 * @Author: Zhe Chen
 * @Date: 2021-04-23 14:28:05
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 14:35:38
 * @Description: 记录接口
 */
package com.buaa.lookclipboard.model;

import java.time.LocalDateTime;
import java.util.List;

import javafx.scene.input.DataFormat;

/**
 * 记录接口
 */
public interface IRecord {
    /**
     * 获取ID
     * 
     * @return ID
     */
    String getID();

    /**
     * 获取数据格式
     * 
     * @return 数据格式
     */
    DataFormat getDataFormat();

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    LocalDateTime getCreatedTime();

    /**
     * 获取修改时间
     * 
     * @return 修改时间
     */
    LocalDateTime getModifiedTime();

    /**
     * 获取内容
     * 
     * @return 内容
     */
    String getContent();

    /**
     * 获取是否固定
     * 
     * @return 是否固定
     */
    boolean getIsPinned();

    /**
     * 获取标签列表
     * 
     * @return 标签列表
     */
    List<IRecordTag> getTags();
}