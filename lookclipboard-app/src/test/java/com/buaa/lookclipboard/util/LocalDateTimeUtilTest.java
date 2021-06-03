/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-06-03 14:59:14
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 15:12:33
 * 
 * @Description: 本地日期时间工具类测试
 */
package com.buaa.lookclipboard.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.Test;

/**
 * 本地日期时间工具类测试
 */
public class LocalDateTimeUtilTest {
    /**
     * 测试格式化
     */
    @Test
    public void testFormat() {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeString = LocalDateTimeUtil.format(dateTime, "yyyy");
        assertEquals(dateTimeString, String.valueOf(dateTime.getYear()));
    }

    /**
     * 测试日期时间转为时间戳
     */
    @Test
    public void testToTimeStamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp timestamp = LocalDateTimeUtil.toTimeStamp(dateTime);
        assertTrue(dateTime.equals(timestamp.toLocalDateTime()));
    }

    /**
     * 时间戳转回日期时间
     */
    @Test
    public void testFromTimeStamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp timestamp = LocalDateTimeUtil.toTimeStamp(dateTime);
        assertTrue(dateTime.equals(LocalDateTimeUtil.fromTimeStamp(timestamp)));
    }
}
