/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-12 12:24:23
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:38:35
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
    private final Class<? extends E>[] exceptionTypes;

    @SafeVarargs
    public ExceptionHandler(BiConsumer<E, T> handler, Class<? extends E>... exceptionTypes) {
        this.handler = handler;
        this.exceptionTypes = exceptionTypes;
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
     * 获取异常类型数组
     * 
     * @return 异常类型数组
     */
    public Class<? extends E>[] getExceptionTypes() {
        return exceptionTypes;
    }

    /**
     * 判断是否能够处理异常
     * 
     * @param e 异常
     * @return 是否能够处理异常
     */
    public boolean canHandle(Exception e) {
        Class<? extends E>[] exceptionTypes = getExceptionTypes();
        if (exceptionTypes != null) {
            for (Class<? extends E> exceptionType : exceptionTypes) {
                if (exceptionType != null && exceptionType.isInstance(e)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 处理异常
     * 
     * @param e       异常
     * @param context 上下文
     */
    public void handle(E e, T context) {
        BiConsumer<E, T> handler = getHandler();
        if (handler == null) {
            return;
        }

        handler.accept(e, context);
    }
}
