/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-12 12:24:23
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-12 16:16:47
 * 
 * @Description: 异常处理器
 */
package com.buaa.commons.lang;

import java.util.function.BiConsumer;

/**
 * 异常处理器
 * 
 * @param <E> 异常类型
 * @param <T> 上下文类型
 */
public final class ExceptionHandler<E extends Exception, T extends ITryContext> {
    private final BiConsumer<E, T> handler;
    private final Class<E> exceptionType;

    public ExceptionHandler(BiConsumer<E, T> handler, Class<E> exceptionType) {
        this.handler = handler;
        this.exceptionType = exceptionType;
    }

    /**
     * 获取处理器
     * 
     * @return 处理器
     */
    public BiConsumer<E, T> getHandler() {
        return handler;
    }

    /**
     * 获取异常类型
     * 
     * @return 异常类型
     */
    public Class<E> getExceptionType() {
        return exceptionType;
    }

    /**
     * 判断是否能够处理异常
     * 
     * @param e 异常
     * @return 是否能够处理异常
     */
    public boolean canHandle(Exception e) {
        Class<E> exceptionType = getExceptionType();
        return exceptionType != null ? exceptionType.isInstance(e) : false;
    }

    /**
     * 处理异常
     * 
     * @param e 异常
     */
    public void handle(E e, T context) {
        BiConsumer<E, T> handler = getHandler();
        if (handler == null) {
            return;
        }

        handler.accept(e, context);
    }
}
