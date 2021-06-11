/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-03-26 19:34:56
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-11 14:46:12
 * 
 * @Description: 懒加载类
 */
package com.buaa.commons.foundation;

import com.buaa.commons.foundation.function.ThrowingSupplier;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 懒加载类
 */
public class Lazy<T> {
    private boolean isValueCreated;
    private ThrowingSupplier<T, Exception> valueFactory;
    private T value;

    private final Object syncRoot = new Object();

    /**
     * 指定值加载器进行构造
     * 
     * @param valueFactory 值加载器
     */
    public Lazy(ThrowingSupplier<T, Exception> valueFactory) {
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
                    try {
                        value = valueFactory != null ? valueFactory.get() : null;
                    } catch (Exception e) {
                        ExceptionUtils.rethrow(e);
                    }
                    isValueCreated = true;
                }
            }
        }

        return value;
    }
}
