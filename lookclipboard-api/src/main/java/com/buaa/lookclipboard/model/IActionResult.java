/*
 * @Author: Zhe Chen
 * @Date: 2021-04-26 21:32:06
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 21:50:44
 * @Description: 操作结果接口
 */
package com.buaa.lookclipboard.model;

/**
 * 操作结果接口
 */
public interface IActionResult {
    /**
     * 获取信息
     * 
     * @return 信息
     */
    String getInfo();

    /**
     * 获取状态码
     * 
     * @return 状态码
     */
    int getStatusCode();
}
