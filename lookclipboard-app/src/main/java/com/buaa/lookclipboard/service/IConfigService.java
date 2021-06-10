/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-06-10 11:55:34
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-10 11:57:48
 * 
 * @Description: 配置服务接口
 */
package com.buaa.lookclipboard.service;

/**
 * 配置服务接口
 */
public interface IConfigService extends IAppService {
    /**
     * 获取配置
     * 
     * @return 配置Json
     */
    String getConfig();
}
