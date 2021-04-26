/*
 * @Author: Zhe Chen
 * @Date: 2021-03-26 19:34:56
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 13:11:40
 * @Description: 懒加载类
 */
package com.buaa.appmodel.foundation;

import java.util.function.Supplier;

/**
 * 懒加载类
 */
public class Lazy<T> {
    private boolean isValueCreated;
    private Supplier<T> valueFactory;
    private T value;

    private final Object syncRoot = new Object();

    public Lazy(Supplier<T> valueFactory) {
        this.valueFactory = valueFactory;
    }

    /**
     * 获取是否已创建值
     * 
     * @return 是否已创建值
     */
    public boolean getIsValueCreated() {
        return isValueCreated;
    }

    /**
     * 获取值
     * 
     * @return 值
     */
    public T getValue() {
        if (!isValueCreated) {
            synchronized (syncRoot) {
                if (!isValueCreated) {
                    value = valueFactory != null ? valueFactory.get() : null;
                    isValueCreated = true;
                }
            }
        }

        return value;
    }
}
