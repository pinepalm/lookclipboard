/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-14 11:26:06
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 14:48:28
 * 
 * @Description: Json工具类测试
 */
package com.buaa.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

/**
 * Json工具类测试
 */
public class JsonUtilTest {
    /**
     * 测试文件解析
     */
    @Test
    public void testFilesParse() {
        Set<File> files = JsonUtil.parse("[\"C:\\\\Program Files\"]", new TypeReference<HashSet<File>>() { });
        assertTrue(files.contains(new File("C:\\Program Files")));
    }

    /**
     * 测试文件转Json字符串
     */
    @Test
    public void testFilesStringify() {
        Set<File> files = new HashSet<>();
        files.add(new File("C:\\Program Files"));
        assertEquals(JsonUtil.stringify(files), "[\"C:\\\\Program Files\"]");
    }
}
