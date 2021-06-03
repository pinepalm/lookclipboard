/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-06-03 14:58:45
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 15:18:06
 * 
 * @Description: 数据类型工具类测试
 */
package com.buaa.lookclipboard.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import javafx.scene.input.DataFormat;

/**
 * 数据类型工具类测试
 */
public class DataFormatUtilTest {
    /**
     * 测试数据类型转为JSON字符串
     */
    @Test
    public void testToJSON() {
        String res = DataFormatUtil.toJSON(DataFormat.PLAIN_TEXT);
        assertEquals(res, "[\"text/plain\"]");
    }

    /**
     * 测试JSON字符串转回数据类型
     */
    @Test
    public void testFromJSON() {
        DataFormat res = DataFormatUtil.fromJSON("[\"text/plain\"]");
        assertEquals(res, DataFormat.PLAIN_TEXT);
    }
}
