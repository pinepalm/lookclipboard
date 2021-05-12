/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-02 00:12:53
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 14:40:44
 * 
 * @Description: 应用配置
 */
package com.buaa.lookclipboard;

import java.io.File;

import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.util.StorageUtil;
import net.harawata.appdirs.AppDirsFactory;

/**
 * 应用配置
 */
public final class AppConfig {
    private final static String APP_AUTHOR = "buaa";
    private final static String APP_NAME = "lookclipboard";
    private final static String APP_DISPLAY_NAME = "LookClipboard";
    private final static String APP_DATA = "appdata";

    private final static Lazy<AppConfig> instance = new Lazy<>(() -> new AppConfig());

    /**
     * 获取应用配置实例
     * 
     * @return 应用配置实例
     */
    public static AppConfig getInstance() {
        return instance.getValue();
    }

    private AppConfig() {

    }

    public String getAuthor() {
        return APP_AUTHOR;
    }

    public String getName() {
        return APP_NAME;
    }

    public String getDisplayName() {
        return APP_DISPLAY_NAME;
    }

    public File getAppFolder() {
        String path = AppDirsFactory.getInstance().getUserDataDir(getName(), null, getAuthor());
        return StorageUtil.getFolder(path, true, false);
    }

    public File getAppDataFolder() {
        File appFolder = getAppFolder();
        String path = String.format("%s/%s", appFolder.getAbsolutePath(), APP_DATA);
        return StorageUtil.getFolder(path, true, false);
    }
}
