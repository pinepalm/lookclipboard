/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-14 19:38:46
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-14 21:32:16
 * 
 * @Description: 字符串工具类
 */
package com.buaa.commons.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.text.StringSubstitutor;

/**
 * 字符串工具类
 */
public final class StringUtil {
    /**
     * 字符串内插
     * 
     * @param source   原字符串
     * @param valueMap 值映射
     * @return 内插字符串
     */
    public static String interpolate(String source, Map<String, Object> valueMap) {
        return StringSubstitutor.replace(source, valueMap);
    }

    /**
     * 字符串内插
     * 
     * @param source 原字符串
     * @param params 参数列表
     * @return 内插字符串
     */
    public static String interpolate(String source, Object[] params) {
        return interpolate(source, MapUtils.putAll(new HashMap<>(), params));
    }

    /**
     * 将Sql的Like字符串转义
     * 
     * @param sql    Sql字符串
     * @param escape 转义字符
     * @return 转义字符串
     */
    public static String escapeSqlLike(String sql, char escape) {
        if (sql == null) {
            return null;
        }

        String esc = String.valueOf(escape);
        return sql.replace(esc, esc + esc)
                .replace("%", esc + "%")
                .replace("&", esc + "&")
                .replace("_", esc + "_")
                .replace("[", esc + "[")
                .replace("]", esc + "]")
                .replace("(", esc + "(")
                .replace(")", esc + ")");
    }

    private StringUtil() {

    }
}
