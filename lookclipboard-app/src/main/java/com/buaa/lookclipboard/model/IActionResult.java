/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-07 15:26:24
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 15:28:32
 * 
 * @Description: 操作结果接口
 */
package com.buaa.lookclipboard.model;

/**
 * 操作结果接口
 */
public interface IActionResult {
    /**
     * 获取代码
     * 
     * @return 代码
     */
    Integer getCode();

    /**
     * 获取消息
     * 
     * @return 消息
     */
    String getMessage();

    /**
     * 获取数据
     * 
     * @return 数据
     */
    Object getData();
}
