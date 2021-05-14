/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-02 00:12:53
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-13 13:05:28
 * 
 * @Description: 应用配置
 */
package com.buaa.lookclipboard;

import java.io.File;

import com.buaa.commons.foundation.Lazy;
import org.apache.commons.io.FileUtils;
import net.harawata.appdirs.AppDirsFactory;

/**
 * 应用配置
 */
public final class AppConfig {
    private final static String APP_AUTHOR = "buaa";
    private final static String APP_NAME = "lookclipboard";
    private final static String APP_DISPLAY_NAME = "LookClipboard";
    private final static String APP_DATA = "data";
    private final static String APP_LOG = "log";

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
        return FileUtils.getFile(path);
    }

    public File getAppDataFolder() {
        File appFolder = getAppFolder();
        return FileUtils.getFile(appFolder, APP_DATA);
    }

    public File getAppLogFolder() {
        File appFolder = getAppFolder();
        return FileUtils.getFile(appFolder, APP_LOG);
    }
}
