/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-02 00:12:53
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-10 02:21:15
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
    private final static String APP_LOGS = "logs";
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
        File folder = FileUtils.getFile(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    /**
     * 获取应用数据文件夹
     * 
     * @return 应用数据文件夹
     */
    public File getAppDataFolder() {
        File appFolder = getAppFolder();
        File folder = FileUtils.getFile(appFolder, APP_DATA);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    /**
     * 获取应用日志文件夹
     * 
     * @return 应用日志文件夹
     */
    public File getAppLogsFolder() {
        File appFolder = getAppFolder();
        File folder = FileUtils.getFile(appFolder, APP_LOGS);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    /**
     * 获取应用设置文件夹
     * 
     * @return 应用设置文件夹
     */
    public File getAppSettingsFolder() {
        File appFolder = getAppFolder();
        File folder = FileUtils.getFile(appFolder, APP_SETTINGS);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}
