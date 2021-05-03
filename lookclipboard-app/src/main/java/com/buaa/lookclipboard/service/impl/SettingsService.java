/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 21:55:44
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 22:48:36
 * @Description: 设置服务
 */
package com.buaa.lookclipboard.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.util.DoubleUtil;
import com.buaa.lookclipboard.App;
import com.buaa.lookclipboard.AppConfig;
import com.buaa.lookclipboard.service.ISettingsService;

/**
 * 设置服务
 */
public final class SettingsService implements ISettingsService {
    private final static Lazy<SettingsService> instance = new Lazy<>(() -> new SettingsService());

    /**
     * 获取设置服务实例
     * 
     * @return 设置服务实例
     */
    public static SettingsService getInstance() {
        return instance.getValue();
    }

    private final String SETTINGS_PATH = String.format("%s/settings.xml",
            AppConfig.getInstance().getDataFolder().getAbsolutePath());

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
     * {@inheritDoc}
     */
    @Override
    public double getOpacity() {
        return DoubleUtil.tryParse(props.getProperty(OPACITY), 1.0);
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
        props.setProperty(OPACITY, Double.toString(opacity));
        saveProps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getAlwaysOnTop() {
        return Boolean.parseBoolean(props.getProperty(ALWAYS_ON_TOP));
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
        props.setProperty(ALWAYS_ON_TOP, Boolean.toString(alwaysOnTop));
        saveProps();
    }
}
