/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 13:02:35
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-01 09:44:00
 * 
 * @Description: try-catch-finally包装器
 */
package com.buaa.commons.lang;

import java.util.function.Consumer;
import com.buaa.commons.foundation.function.ThrowingConsumer;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * try-catch-finally包装器
 * 
 * @param <T> 上下文类型
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class TryWrapper<T extends ITryContext> {
    private final ThrowingConsumer<T, Exception> tryStatement;
    private final ExceptionHandler<?, ?>[] exceptionHandlers;
    private final Consumer<T> finallyStatement;

    public TryWrapper(
            ThrowingConsumer<T, Exception> tryStatement) {

        this(tryStatement, null);
    }

    public TryWrapper(
            ThrowingConsumer<T, Exception> tryStatement,
            ExceptionHandler<?, ?>[] exceptionHandlers) {

        this(tryStatement, exceptionHandlers, null);
    }

    public TryWrapper(
            ThrowingConsumer<T, Exception> tryStatement,
            ExceptionHandler<?, ?>[] exceptionHandlers, 
            Consumer<T> finallyStatement) {
                
        this.tryStatement = tryStatement;
        this.exceptionHandlers = exceptionHandlers;
        this.finallyStatement = finallyStatement;
    }

    /**
     * 激活(上下文为null)
     */
    public void invoke() {
        invoke(null);
    }

    /**
     * 激活
     * 
     * @param context 上下文
     */
    public void invoke(T context) {
        try {
            if (tryStatement != null) {
                tryStatement.accept(context);
            }
        } catch (Exception e) {
            boolean isHandled = false;

            if (exceptionHandlers != null) {
                for (ExceptionHandler exceptionHandler : exceptionHandlers) {
                    if (exceptionHandler != null && exceptionHandler.canHandle(e)) {
                        exceptionHandler.handle(e, context);
                        isHandled=true;

                        break;
                    }
                }
            }
            
            if(!isHandled) {
                ExceptionUtils.rethrow(e);
            }
        } finally {
            if (finallyStatement != null) {
                finallyStatement.accept(context);
            }
        }
    }
}
