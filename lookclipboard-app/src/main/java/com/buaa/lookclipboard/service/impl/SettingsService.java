/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-22 21:55:44
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-04 15:31:26
 * 
 * @Description: 设置服务
 */
package com.buaa.lookclipboard.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.buaa.commons.foundation.Lazy;
import com.buaa.lookclipboard.App;
import com.buaa.lookclipboard.AppConfig;
import com.buaa.lookclipboard.service.ISettingsService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 设置服务
 */
public final class SettingsService implements ISettingsService {
    private final static String APP_SETTINGS = "AppSettings";
    private final static String OPACITY = "opacity";
    private final static String ALWAYS_ON_TOP = "alwaysOnTop";
    
    private final static String SETTINGS_NAME = "settings.xml";
    private final static String SETTINGS_PATH = FileUtils.getFile(AppConfig.getInstance().getAppSettingsFolder(), SETTINGS_NAME).getAbsolutePath();

    private final static Lazy<SettingsService> instance = new Lazy<>(() -> new SettingsService());

    /**
     * 获取设置服务实例
     * 
     * @return 设置服务实例
     */
    public static SettingsService getInstance() {
        return instance.getValue();
    }

    private final File SETTINGS_FILE = new File(SETTINGS_PATH);

    private final Properties props = new Properties();

    private SettingsService() {
        try {
            if (!SETTINGS_FILE.exists()) {
                SETTINGS_FILE.createNewFile();
            }

            FileInputStream settingsInput = new FileInputStream(SETTINGS_FILE);
            props.loadFromXML(settingsInput);
            settingsInput.close();
        } catch (Exception e) {
            LogService.getInstance().warn("settings load failed", e);
        }
    }

    private String getProperty(String key) {
        return props.getProperty(key);
    }

    private void setProperty(String key, String value) {
        props.setProperty(key, value);
        try {
            FileOutputStream settingsOutput = new FileOutputStream(SETTINGS_FILE);
            props.storeToXML(settingsOutput, APP_SETTINGS);
            settingsOutput.close();
        } catch (IOException e) {
            LogService.getInstance().warn("settings save failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getOpacity() {
        double opacity = NumberUtils.toDouble(getProperty(OPACITY), 1d);
        return Math.max(0d, Math.min(opacity, 1d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpacity(double opacity) {
        opacity = Math.max(0d, Math.min(opacity, 1d));

        if (getOpacity() == opacity) {
            return;
        }

        App.getStage().setOpacity(opacity);
        setProperty(OPACITY, Double.toString(opacity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getAlwaysOnTop() {
        return Boolean.parseBoolean(getProperty(ALWAYS_ON_TOP));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        if (getAlwaysOnTop() == alwaysOnTop) {
            return;
        }

        App.getStage().setAlwaysOnTop(alwaysOnTop);
        setProperty(ALWAYS_ON_TOP, Boolean.toString(alwaysOnTop));
    }
}
