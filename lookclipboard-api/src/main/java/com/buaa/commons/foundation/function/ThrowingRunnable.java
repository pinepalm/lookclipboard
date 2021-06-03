/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 17:34:11
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:31:42
 * 
 * @Description: 可抛出异常的 Runnable
 */
package com.buaa.commons.foundation.function;

import java.lang.Runnable;

/**
 * 可抛出异常的 {@link Runnable}
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
     * @throws E 可能抛出的异常
     */
    void run() throws E;
}
