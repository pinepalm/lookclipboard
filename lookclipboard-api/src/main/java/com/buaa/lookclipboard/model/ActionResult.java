/*
 * @Author: Zhe Chen
 * @Date: 2021-04-26 21:51:45
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 14:58:58
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatusCode() {
        return statusCode;
    }
}