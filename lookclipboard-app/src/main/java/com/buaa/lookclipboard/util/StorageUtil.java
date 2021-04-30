/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 21:56:45
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 20:08:48
 * @Description: 储存工具类
 */
package com.buaa.lookclipboard.util;

import java.io.File;

/**
 * 储存工具类
 */
public final class StorageUtil {
    /**
     * 获取本地应用数据文件夹(该文件夹下存有很多应用的数据，因此还要进一步创建自身应用的文件夹)
     * 
     * @return 本地应用数据文件夹
     */
    public static File getLocalAppDataFolder() {
        return new File(System.getenv("LOCALAPPDATA"));
    }

    private StorageUtil() {

    }
}
