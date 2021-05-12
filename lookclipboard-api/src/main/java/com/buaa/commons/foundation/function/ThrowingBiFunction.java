/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-07 23:59:23
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-08 00:22:51
 * 
 * @Description: 可抛出异常的 BiFunction
 */
package com.buaa.commons.foundation.function;

import java.util.Objects;

/**
 * 可抛出异常的 BiFunction
 * 
 * @param <T>
 * @param <U>
 * @param <R>
 * @param <E>
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
     * @throws E
     */
    R apply(T t, U u) throws E;

    /**
     * 生成复合的 ThrowingBiFunction
     *
     * @param <V>   传递参数类型
     * @param after 在此 ThrowingBiFunction 执行之后执行的 ThrowingFunction
     * @return 复合的 ThrowingBiFunction
     * @throws NullPointerException 若{@code after}为null
     */
    default <V> ThrowingBiFunction<T, U, V, E> andThen(
            ThrowingFunction<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
