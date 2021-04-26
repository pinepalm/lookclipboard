/*
 * @Author: Zhe Chen
 * @Date: 2021-04-23 14:28:05
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 13:14:03
 * @Description: 记录接口
 */
package com.buaa.lookclipboard.domain;

import java.time.LocalDateTime;

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
     * 获取信息
     * 
     * @return 信息
     */
    String getInfo();

    /**
     * 获取是否固定
     * 
     * @return 是否固定
     */
    boolean getIsPinned();
}