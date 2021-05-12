/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-06 20:59:14
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-08 00:17:01
 * 
 * @Description: 可抛出异常的 Consumer
 */
package com.buaa.commons.foundation.function;

import java.util.Objects;

/**
 * 可抛出异常的 Consumer
 * 
 * @param <T> 参数类型
 * @param <E> 异常类型
 * 
 * @see Consumer
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> {
    /**
     * 接收给定的参数并执行
     * 
     * @param t 参数
     * @throws E
     */
    void accept(T t) throws E;

    /**
     * 生成复合的 ThrowingConsumer
     *
     * @param after 在此 ThrowingConsumer 执行之后执行的 ThrowingConsumer
     * @return 复合的 ThrowingConsumer
     * @throws NullPointerException 若{@code after}为null
     */
    default ThrowingConsumer<T, E> andThen(ThrowingConsumer<? super T, E> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
