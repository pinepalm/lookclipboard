/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-14 11:26:06
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-14 20:39:36
 * 
 * @Description: Json工具类测试
 */
package com.buaa.commons.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

/**
 * Json工具类测试
 */
public class JsonUtilTest {
    @Test
    public void print() {
        Set<File> files =
                JsonUtil.parse("[\"C:\\\\Program Files\"]", new TypeReference<HashSet<File>>() {
                });
        System.out.println(files.contains(new File("C:\\Program Files")));
    }
}
