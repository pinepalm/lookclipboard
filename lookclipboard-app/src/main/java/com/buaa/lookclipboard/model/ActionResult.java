/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-26 21:51:45
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 15:42:56
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
