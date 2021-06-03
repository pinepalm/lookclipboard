/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-02 00:12:53
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 19:14:27
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
    private final static String APP_SETTINGS = "settings";

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

    /**
     * 获取应用作者
     * 
     * @return 应用作者
     */
    public String getAuthor() {
        return APP_AUTHOR;
    }

    /**
     * 获取应用名称
     * 
     * @return 应用名称
     */
    public String getName() {
        return APP_NAME;
    }

    /**
     * 获取应用显示名称
     * 
     * @return 应用显示名称
     */
    public String getDisplayName() {
        return APP_DISPLAY_NAME;
    }

    /**
     * 获取应用文件夹
     * 
     * @return 应用文件夹
     */
    public File getAppFolder() {
        String path = AppDirsFactory.getInstance().getUserDataDir(getName(), null, getAuthor());
        return FileUtils.getFile(path);
    }

    /**
     * 获取应用数据文件夹
     * 
     * @return 应用数据文件夹
     */
    public File getAppDataFolder() {
        File appFolder = getAppFolder();
        return FileUtils.getFile(appFolder, APP_DATA);
    }

    /**
     * 获取应用日志文件夹
     * 
     * @return 应用日志文件夹
     */
    public File getAppLogFolder() {
        File appFolder = getAppFolder();
        return FileUtils.getFile(appFolder, APP_LOG);
    }

    /**
     * 获取应用设置文件夹
     * 
     * @return 应用设置文件夹
     */
    public File getAppSettingsFolder() {
        File appFolder = getAppFolder();
        return FileUtils.getFile(appFolder, APP_SETTINGS);
    }
}
