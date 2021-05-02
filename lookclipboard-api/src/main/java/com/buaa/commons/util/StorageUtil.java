/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 21:56:45
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-01 14:04:10
 * @Description: 储存工具类
 */
package com.buaa.commons.util;

/**
 * 储存工具类
 */
public final class StorageUtil {
    /**
     * 获取本地应用数据文件夹路径(该文件夹下存有很多应用的数据，因此还要进一步创建自身应用的文件夹)
     * 
     * @return 本地应用数据文件夹路径
     */
    public static String getLocalAppDataFolderPath() {
        return System.getenv("LOCALAPPDATA");
    }

    private StorageUtil() {

    }
}
