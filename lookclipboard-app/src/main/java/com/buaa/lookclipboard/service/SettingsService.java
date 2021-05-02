/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 21:55:44
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 14:25:29
 * @Description: 设置服务
 */
package com.buaa.lookclipboard.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.buaa.commons.util.DoubleUtil;
import com.buaa.lookclipboard.App;
import com.buaa.lookclipboard.AppConfig;

/**
 * 设置服务
 */
public final class SettingsService {
    /**
     * 设置服务实例
     */
    public final static SettingsService instance = new SettingsService();

    private final String SETTINGS_PATH = String.format("%s/settings.xml",
            AppConfig.instance.getDataFolder().getAbsolutePath());

    private final String OPACITY = "opacity";
    private final String ALWAYS_ON_TOP = "alwaysOnTop";

    private final File SETTINGS_FILE = new File(SETTINGS_PATH);

    private Properties props;

    private SettingsService() {
        try {
            if (!SETTINGS_FILE.exists()) {
                SETTINGS_FILE.createNewFile();
            }

            FileInputStream settingsInput = new FileInputStream(SETTINGS_FILE);
            props = new Properties();
            props.loadFromXML(settingsInput);
            settingsInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveProps() {
        try {
            FileOutputStream settingsOutput = new FileOutputStream(SETTINGS_FILE);
            props.storeToXML(settingsOutput, "AppSettings");
            settingsOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取不透明度
     * 
     * @return 不透明度
     */
    public double getOpacity() {
        return DoubleUtil.tryParse(props.getProperty(OPACITY), 1.0);
    }

    /**
     * 设置不透明度
     * 
     * @param opacity 不透明度
     */
    public void setOpacity(double opacity) {
        opacity = Math.max(0d, Math.min(opacity, 1d));
        
        if(getOpacity() == opacity) {
            return;
        }

        App.getStage().setOpacity(opacity);
        props.setProperty(OPACITY, Double.toString(opacity));
        saveProps();
    }

    /**
     * 获取是否置顶
     * 
     * @return 是否置顶
     */
    public boolean getAlwaysOnTop() {
        return Boolean.parseBoolean(props.getProperty(ALWAYS_ON_TOP));
    }

    /**
     * 设置是否置顶
     * 
     * @param alwaysOnTop 是否置顶
     */
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        if(getAlwaysOnTop() == alwaysOnTop) {
            return;
        }

        App.getStage().setAlwaysOnTop(alwaysOnTop);
        props.setProperty(ALWAYS_ON_TOP, Boolean.toString(alwaysOnTop));
        saveProps();
    }
}
