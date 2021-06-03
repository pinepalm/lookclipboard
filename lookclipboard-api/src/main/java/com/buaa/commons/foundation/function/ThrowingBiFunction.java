/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-07 23:59:23
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:27:29
 * 
 * @Description: 可抛出异常的 BiFunction
 */
package com.buaa.commons.foundation.function;

import java.util.function.BiFunction;
import java.util.Objects;

/**
 * 可抛出异常的 {@link BiFunction}
 * 
 * @param <T> 参数1类型
 * @param <U> 参数2类型
 * @param <R> 结果类型
 * @param <E> 异常类型
 * 
 * @see BiFunction
 */
@FunctionalInterface
public interface ThrowingBiFunction<T, U, R, E extends Throwable> {
    /**
     * 接收给定的两个参数并返回结果
     * 
     * @param t 参数1
     * @param u 参数2
     * @return 结果
     * @throws E 可能抛出的异常
     */
    R apply(T t, U u) throws E;

    /**
     * 生成复合的 {@link ThrowingBiFunction}
     *
     * @param <V>   传递参数类型
     * @param after 在此 {@link ThrowingBiFunction} 执行之后执行的 {@link ThrowingFunction}
     * @return 复合的 {@link ThrowingBiFunction}
     * @throws NullPointerException 若{@code after}为null
     */
    default <V> ThrowingBiFunction<T, U, V, E> andThen(
            ThrowingFunction<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
