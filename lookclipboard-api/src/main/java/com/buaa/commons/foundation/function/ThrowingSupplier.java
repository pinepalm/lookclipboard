/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 16:24:52
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-11 16:28:02
 * 
 * @Description: 可抛出异常的 Supplier
 */
package com.buaa.commons.foundation.function;

/**
 * 可抛出异常的 Supplier
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
     * @throws E
     */
    T get() throws E;
}
