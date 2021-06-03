/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-06-03 14:49:07
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 14:56:07
 * 
 * @Description: 字符串工具类测试
 */
package com.buaa.commons.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 字符串工具类测试
 */
public class StringUtilTest {
    /**
     * 测试内插
     */
    @Test
    public void testInterpolate() {
        String res = StringUtil.interpolate("hello, ${world}!", new Object[][] {{"world", "Java"}});
        assertEquals(res, "hello, Java!");
    }

    /**
     * 测试Sql的Like字符串转义
     */
    @Test
    public void testEscapeSqlLike() {
        String res = StringUtil.escapeSqlLike("%///hello[world]%", '/');
        assertEquals(res, "/%//////hello/[world/]/%");
    }
}
