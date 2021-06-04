/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-07 10:48:08
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-04 15:20:56
 * 
 * @Description: 操作结果代码
 */
package com.buaa.lookclipboard.model;

/**
 * 操作结果代码
 */
public enum ActionResultCode {
    SUCCESS(200, "Success"),

    JSON_PARSE_ERROR(400001, "Json Parse Error"), 
    PARAMS_ERROR(400002, "Params Error"),

    RECORD_NOT_EXISTS(404001, "Record Not Exists"), 
    RECORD_EXTENSION_NOT_EXISTS(404002, "Record Extension Not Exists"),

    INTERNAL_ERROR(500001, "Internal Error"), 
    INTERNAL_SQL_ERROR(500002, "Internal Sql Error");

    private final Integer code;
    private final String message;

    private ActionResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取代码
     * 
     * @return 代码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取消息
     * 
     * @return 消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 转为操作结果
     * 
     * @param <T>  数据类型
     * @param data 数据
     * @return 操作结果
     */
    public <T> IActionResult asResult(T data) {
        return new ActionResult<T>(this, data);
    }
}
