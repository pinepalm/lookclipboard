/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-26 21:51:45
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-11 14:49:38
 * 
 * @Description: 操作结果
 */
package com.buaa.lookclipboard.model;

/**
 * 操作结果
 */
public class ActionResult<T> implements IActionResult {
    private final Integer code;
    private final String message;
    private final T data;

    /**
     * 指定结果代码和数据进行构造
     * 
     * @param code 结果代码
     * @param data 数据
     */
    public ActionResult(ActionResultCode code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getData() {
        return data;
    }
}
