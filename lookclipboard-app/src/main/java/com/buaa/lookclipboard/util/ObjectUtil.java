/*
 * @Author: Zhe Chen
 * @Date: 2021-04-30 15:55:47
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 17:13:36
 * @Description: 对象工具
 */
package com.buaa.lookclipboard.util;

/**
 * 对象工具
 */
public final class ObjectUtil {
    /**
     * 将对象转为JavaScript字符串, 即用单引号(')把对象转换后的字符串包裹起来
     * 
     * @param obj 对象
     * @return JavaScript字符串
     */
    public static String asJavaScriptString(Object obj) {
        if (obj == null)
            return null;

        return String.format("'%s'", obj.toString().replace("\\", "\\\\").replace("'", "\\'"));
    }

    /**
     * 将对象转为Sql字符串, 即用单引号(')把对象转换后的字符串包裹起来
     * 
     * @param obj 对象
     * @return Sql字符串
     */
    public static String asSqlString(Object obj) {
        if (obj == null)
            return null;

        return String.format("'%s'", obj.toString().replace("'", "''"));
    }
    
    private ObjectUtil() {

    }
}