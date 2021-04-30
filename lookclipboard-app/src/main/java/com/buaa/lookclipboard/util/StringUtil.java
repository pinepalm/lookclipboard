/*
 * @Author: Zhe Chen
 * @Date: 2021-04-30 15:55:47
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 16:07:14
 * @Description: 字符串工具
 */
package com.buaa.lookclipboard.util;

/**
 * 字符串工具
 */
public final class StringUtil {
    /**
     * 将内容转为JavaScript字符串, 即用单引号(')把内容包裹起来
     * 
     * @param content 内容
     * @return JavaScript字符串
     */
    public static String asJavaScriptString(String content) {
        if (content == null)
            return null;

        return String.format("'%s'", content.replace("\\", "\\\\").replace("'", "\\'"));
    }
    
    private StringUtil() {

    }
}