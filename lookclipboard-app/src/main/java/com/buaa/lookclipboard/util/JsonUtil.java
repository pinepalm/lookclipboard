/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-26 22:25:57
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 23:13:53
 * 
 * @Description: JSON工具类
 */
package com.buaa.lookclipboard.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;

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
        return stringify(value, false);
    }

    /**
     * 将对象转为JSON字符串
     * 
     * @param value          对象值
     * @param throwException 是否抛出异常
     * @return JSON字符串
     * @throws JsonProcessingException
     */
    public static String stringify(Object value, boolean throwException) {
        try {
            ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            if (throwException) {
                ExceptionUtils.rethrow(e);
            }
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSON字符串解析为对象
     * 
     * @param <T>       对象类型
     * @param content   JSON字符串
     * @param valueType 对象类
     * @return 对象
     */
    public static <T> T parse(String content, Class<T> valueType) {
        return parse(content, valueType, false);
    }

    /**
     * 将JSON字符串解析为对象
     * 
     * @param <T>            对象类型
     * @param content        JSON字符串
     * @param valueType      对象类
     * @param throwException 是否抛出异常
     * @return 对象
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    public static <T> T parse(String content, Class<T> valueType, boolean throwException) {
        if (content == null) {
            return null;
        }

        try {
            ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            if (throwException) {
                ExceptionUtils.rethrow(e);
            }
            e.printStackTrace();
            return null;
        }
    }

    private JsonUtil() {

    }
}
