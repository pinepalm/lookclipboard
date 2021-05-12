/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 13:36:36
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-11 17:02:58
 * 
 * @Description: 单例集合(由于Java类型擦除机制，所以不支持泛型)
 */
package com.buaa.commons.foundation.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.buaa.commons.foundation.function.ThrowingSupplier;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 单例集合(由于Java类型擦除机制，所以不支持泛型)
 */
public final class SingletonCollection {
    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    /**
     * 获取实例
     * 
     * @param <T>          实例类型
     * @param valueFactory 对象工厂
     * @param valueType    对象类
     * @return 实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getInstance(ThrowingSupplier<T, Exception> valueFactory, Class<T> valueType) {
        T instance = (T) instances.get(valueType);
        if (instance == null) {
            try {
                instance = valueFactory.get();
            } catch (Exception e) {
                ExceptionUtils.rethrow(e);
            }
            instances.put(valueType, instance);
        }

        return instance;
    }

    /**
     * 获取实例
     * 
     * @param <T>       实例类型
     * @param valueType 对象类
     * @return 实例
     */
    public <T> T getInstance(Class<T> valueType) {
        return getInstance(() -> valueType.newInstance(), valueType);
    }
}
