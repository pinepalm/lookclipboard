/*
 * @Author: Zhe Chen
 * @Date: 2021-05-02 16:56:43
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 17:20:43
 * @Description: 本地日期时间工具类
 */
package com.buaa.lookclipboard.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 本地日期时间工具类
 */
public final class LocalDateTimeUtil {
    /**
     * "yyyy-MM-dd HH:mm:ss.SSS"格式化字符串
     */
    public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 格式化日期时间
     * 
     * @param dateTime 日期时间
     * @param pattern  格式化字符串
     * @return 格式化的日期时间字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null)
            return null;

        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    private LocalDateTimeUtil() {

    }
}