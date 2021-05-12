/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-02 20:41:06
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-11 12:25:10
 * 
 * @Description: 事件类
 */
package com.buaa.commons.lang.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件类
 */
public class Event<T extends EventArgs> {
    private List<IEventHandler<T>> handlers = new ArrayList<>();

    /**
     * 添加事件处理器
     * 
     * @param handler 处理器
     */
    public void addEventHandler(IEventHandler<T> handler) {
        handlers.add(handler);
    }

    /**
     * 移除事件处理器
     * 
     * @param handler 处理器
     */
    public void removeEventHandler(IEventHandler<T> handler) {
        handlers.remove(handler);
    }

    /**
     * 激活事件
     * 
     * @param sender 发送者
     * @param args   参数
     */
    public void invoke(Object sender, T args) {
        for (int i = handlers.size() - 1; i >= 0; i--) {
            handlers.get(i).handle(sender, args);
        }
    }
}
