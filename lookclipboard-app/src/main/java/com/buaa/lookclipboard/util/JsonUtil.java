/*
 * @Author: Zhe Chen
 * @Date: 2021-04-26 22:25:57
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 15:56:48
 * @Description: JSON工具类
 */
package com.buaa.lookclipboard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * JSON工具类
 */
public final class JsonUtil {
    /**
     * 将对象转为JSON字符串
     * 
     * @param value 对象值
     * @return JSON字符串
     */
    public static String stringify(Object value) {
        try {
            ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JsonUtil() {

    }
}