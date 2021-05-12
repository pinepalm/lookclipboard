/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-22 21:56:45
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 14:42:21
 * 
 * @Description: 储存工具类
 */
package com.buaa.commons.util;

import java.io.File;

/**
 * 储存工具类
 */
public final class StorageUtil {
    /**
     * 获取文件夹
     * 
     * @param path              文件夹路径
     * @param createIfNotExists 当不存在时是否创建
     * @param shouldHaveParent  是否应该拥有父文件夹
     * @return 文件夹
     */
    public static File getFolder(String path, boolean createIfNotExists, boolean shouldHaveParent) {
        try {
            File folder = new File(path);
            return folder.isDirectory()
                    || (createIfNotExists ? (shouldHaveParent ? folder.mkdir() : folder.mkdirs())
                            : false) ? folder : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private StorageUtil() {

    }
}
