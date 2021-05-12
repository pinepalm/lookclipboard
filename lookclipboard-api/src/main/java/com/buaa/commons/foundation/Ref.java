/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-29 16:46:03
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-04-30 20:18:14
 * 
 * @Description: 引用类
 */
package com.buaa.commons.foundation;

/**
 * 引用类
 * 
 * @param <T> 引用类型
 */
public final class Ref<T> {
    private T obj;

    /**
     * 获取引用对象
     * 
     * @return 引用对象
     */
    public T get() {
        return obj;
    }

    /**
     * 设置引用对象
     * 
     * @param obj 引用对象
     */
    public void set(T obj) {
        this.obj = obj;
    }
}
