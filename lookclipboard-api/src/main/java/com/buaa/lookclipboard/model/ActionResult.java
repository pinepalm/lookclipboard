/*
 * @Author: Zhe Chen
 * @Date: 2021-04-26 21:51:45
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 21:54:01
 * @Description: 操作结果
 */
package com.buaa.lookclipboard.model;

/**
 * 操作结果
 */
public class ActionResult implements IActionResult {
    private final String info;
    private final int statusCode;

    public ActionResult(String info, int statusCode) {
        this.info = info;
        this.statusCode = statusCode;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}