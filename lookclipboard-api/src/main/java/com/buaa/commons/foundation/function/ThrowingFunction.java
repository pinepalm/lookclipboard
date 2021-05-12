/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-08 00:02:03
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-08 00:19:26
 * 
 * @Description: 可抛出异常的 Function
 */
package com.buaa.commons.foundation.function;

import java.util.Objects;

/**
 * 可抛出异常的 Function
 * 
 * @param <T> 参数类型
 * @param <R> 结果类型
 * @param <E> 异常类型
 * 
 * @see Function
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {
    /**
     * 接收给定的参数并返回结果
     * 
     * @param t 参数
     * @return 结果
     * @throws E
     */
    R apply(T t) throws E;

    /**
     * 生成复合的 ThrowingFunction
     * 
     * @param <V>    传递参数类型
     * @param before 在此 ThrowingFunction 执行之前执行的 ThrowingFunction
     * @return 复合的 ThrowingFunction
     * @throws NullPointerException 若{@code before}为null
     * 
     * @see #andThen(ThrowingFunction)
     */
    default <V> ThrowingFunction<V, R, E> compose(
            ThrowingFunction<? super V, ? extends T, E> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * 生成复合的 ThrowingFunction
     * 
     * @param <V>   传递参数类型
     * @param after 在此 ThrowingFunction 执行之后执行的 ThrowingFunction
     * @return 复合的 ThrowingFunction
     * @throws NullPointerException 若{@code after}为null
     * 
     * @see #compose(ThrowingFunction)
     */
    default <V> ThrowingFunction<T, V, E> andThen(
            ThrowingFunction<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     * 生成返回自身参数的 ThrowingFunction
     * 
     * @param <T> 参数类型
     * @param <E> 异常类型
     * @return 返回自身参数的 ThrowingFunction
     */
    static <T, E extends Throwable> ThrowingFunction<T, T, E> identity() {
        return t -> t;
    }
}
