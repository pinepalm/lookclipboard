/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 16:24:52
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:32:50
 * 
 * @Description: 可抛出异常的 Supplier
 */
package com.buaa.commons.foundation.function;

import java.util.function.Supplier;

/**
 * 可抛出异常的 {@link Supplier}
 * 
 * @param <T> 结果类型
 * @param <E> 异常类型
 * 
 * @see Supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> {
    /**
     * 返回结果
     * 
     * @return 结果
     * @throws E 可能抛出的异常
     */
    T get() throws E;
}
