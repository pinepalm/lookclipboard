/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-07 10:48:08
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-12 17:03:15
 * 
 * @Description: 操作结果代码
 */
package com.buaa.lookclipboard.model;

/**
 * 操作结果代码
 */
public enum ActionResultCode {
    SUCCESS(200, "成功"),

    JSON_PARSE_ERROR(400001, "JSON解析错误"), 
    PARAMS_ERROR(400002, "请求参数错误"),

    RECORD_NOT_EXISTS(404001, "记录不存在"), 
    RECORD_EXTENSION_NOT_EXISTS(404002, "缺失该记录对应的数据格式扩展"),

    INTERNAL_ERROR(500001, "内部错误"), 
    INTERNAL_SQL_ERROR(500002, "内部SQL错误");

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
