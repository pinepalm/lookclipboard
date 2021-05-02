/*
 * @Author: Zhe Chen
 * @Date: 2021-04-09 10:29:45
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 13:01:15
 * @Description: 整型工具类
 */
package com.buaa.commons.util;

/**
 * 整型工具类
 */
public final class IntegerUtil {
    /**
     * 尝试将字符串转为整型
     * 
     * @param s            字符串
     * @param defaultValue 默认值
     * @return 整型
     */
    public static int tryParse(String s, int defaultValue) {
        return tryParse(s, Integer.valueOf(defaultValue));
    }

    /**
     * 尝试将字符串转为整型
     * 
     * @param s            字符串
     * @param radix        进制
     * @param defaultValue 默认值
     * @return 整型
     */
    public static int tryParse(String s, int radix, int defaultValue) {
        return tryParse(s, radix, Integer.valueOf(defaultValue));
    }

    /**
     * 尝试将字符串转为整型
     * 
     * @param s            字符串
     * @param defaultValue 默认值
     * @return 整型
     */
    public static Integer tryParse(String s, Integer defaultValue) {
        return tryParse(s, 10, defaultValue);
    }

    /**
     * 尝试将字符串转为整型
     * 
     * @param s            字符串
     * @param radix        进制
     * @param defaultValue 默认值
     * @return 整型
     */
    public static Integer tryParse(String s, int radix, Integer defaultValue) {
        try {
            return Integer.parseInt(s, radix);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private IntegerUtil() {

    }
}