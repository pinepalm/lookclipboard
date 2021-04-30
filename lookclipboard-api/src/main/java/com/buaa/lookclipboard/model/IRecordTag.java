/*
 * @Author: Zhe Chen
 * @Date: 2021-04-30 10:35:12
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 10:39:18
 * @Description: 记录标签接口
 */
package com.buaa.lookclipboard.model;

/**
 * 记录标签接口
 */
public interface IRecordTag {
    /**
     * 获取ID
     * 
     * @return ID
     */
    String getID();

    /**
     * 获取名称
     * 
     * @return 名称
     */
    String getName();
}