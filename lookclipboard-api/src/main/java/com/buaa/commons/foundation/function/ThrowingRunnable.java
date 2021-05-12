/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 17:34:11
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-11 17:36:31
 * 
 * @Description: 可抛出异常的 Runnable
 */
package com.buaa.commons.foundation.function;

/**
 * 可抛出异常的 Runnable
 * 
 * @param <E> 异常类型
 * 
 * @see Runnable
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {
    /**
     * 执行
     * 
     * @throws E
     */
    void run() throws E;
}
