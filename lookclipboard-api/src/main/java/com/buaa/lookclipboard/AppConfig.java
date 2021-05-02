/*
 * @Author: Zhe Chen
 * @Date: 2021-05-02 00:12:53
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 12:16:52
 * @Description: 应用配置
 */
package com.buaa.lookclipboard;

import java.io.File;

import com.buaa.commons.util.StorageUtil;

/**
 * 应用配置
 */
public final class AppConfig {
    /**
     * 应用配置实例
     */
    public final static AppConfig instance = new AppConfig();

    public String getName() {
        return "lookclipboard";
    }

    public String getDisplayName() {
        return "LookClipboard";
    }

    public File getDataFolder() {
        try {
            String path = String.format("%s/%s", StorageUtil.getLocalAppDataFolderPath(), getName());
            File dataFolder = new File(path);

            return dataFolder.isDirectory() || dataFolder.mkdir() ? dataFolder : null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private AppConfig() {

    }
}
