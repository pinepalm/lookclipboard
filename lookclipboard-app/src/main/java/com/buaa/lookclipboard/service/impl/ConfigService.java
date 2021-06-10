/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-06-10 11:58:37
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-10 12:02:19
 * 
 * @Description: 配置服务
 */
package com.buaa.lookclipboard.service.impl;

import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.util.JsonUtil;
import com.buaa.lookclipboard.AppConfig;
import com.buaa.lookclipboard.service.IConfigService;

/**
 * 配置服务
 */
public final class ConfigService implements IConfigService {
    private final static Lazy<ConfigService> instance = new Lazy<>(() -> new ConfigService());

    /**
     * 获取配置服务实例
     * 
     * @return 配置服务实例
     */
    public static ConfigService getInstance() {
        return instance.getValue();
    }

    private ConfigService() {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getConfig() {
        return JsonUtil.stringify(AppConfig.getInstance());
    }
}
