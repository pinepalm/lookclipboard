/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-02 20:39:52
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-12 16:49:20
 * 
 * @Description: 事件处理类
 */
package com.buaa.commons.lang.event;

/**
 * 事件处理类
 */
public interface IEventHandler<T extends EventArgs> {
    /**
     * 处理
     * 
     * @param sender 发送者
     * @param args   参数
     */
    public void handle(Object sender, T args);
}
