/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-03 22:32:52
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 11:58:18
 * 
 * @Description: 设置服务接口
 */
package com.buaa.lookclipboard.service;

/**
 * 设置服务接口
 */
public interface ISettingsService extends IAppService {
    /**
     * 获取不透明度
     * 
     * @return 不透明度
     */
    double getOpacity();

    /**
     * 设置不透明度
     * 
     * @param opacity 不透明度
     */
    void setOpacity(double opacity);

    /**
     * 获取是否置顶
     * 
     * @return 是否置顶
     */
    boolean getAlwaysOnTop();

    /**
     * 设置是否置顶
     * 
     * @param alwaysOnTop 是否置顶
     */
    void setAlwaysOnTop(boolean alwaysOnTop);
}
