/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 13:02:35
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-11 14:44:17
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
    /**
     * 构建构造器
     * 
     * @param <T> 上下文类型
     * @return 构造器
     */
    public static <T extends ITryContext> Builder<T> builder() {
        return new Builder<T>();
    }

    private final ThrowingConsumer<T, Exception> tryStatement;
    private final ExceptionHandler<?, ?>[] catchStatement;
    private final Consumer<T> finallyStatement;

    /**
     * 指定try语句块进行构造
     * 
     * @param tryStatement try语句块
     */
    public TryWrapper(
            ThrowingConsumer<T, Exception> tryStatement) {

        this(tryStatement, null);
    }

    /**
     * 指定try语句块和catch语句块进行构造
     * 
     * @param tryStatement   try语句块
     * @param catchStatement catch语句块
     */
    public TryWrapper(
            ThrowingConsumer<T, Exception> tryStatement,
            ExceptionHandler<?, ?>[] catchStatement) {

        this(tryStatement, catchStatement, null);
    }

    /**
     * 指定try语句块和catch语句块和finally语句块进行构造
     * 
     * @param tryStatement     try语句块
     * @param catchStatement   catch语句块
     * @param finallyStatement finally语句块
     */
    public TryWrapper(
            ThrowingConsumer<T, Exception> tryStatement,
            ExceptionHandler<?, ?>[] catchStatement, 
            Consumer<T> finallyStatement) {
                
        this.tryStatement = tryStatement;
        this.catchStatement = catchStatement;
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

            if (catchStatement != null) {
                for (ExceptionHandler exceptionHandler : catchStatement) {
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

    /**
     * try-catch-finally包装器构造器
     * 
     * @param <T> 上下文类型
     */
    public static class Builder<T extends ITryContext> {
        private ThrowingConsumer<T, Exception> tryStatement;
        private ExceptionHandler<?, ?>[] catchStatement;
        private Consumer<T> finallyStatement;

        /**
         * 设置try语句块
         * 
         * @param tryStatement try语句块
         * @return 构造器自身
         */
        public Builder<T> tryStatement(ThrowingConsumer<T, Exception> tryStatement) {
            this.tryStatement = tryStatement;
            return this;
        }

        /**
         * 设置catch语句块
         * 
         * @param catchStatement catch语句块
         * @return 构造器自身
         */
        public Builder<T> catchStatement(ExceptionHandler<?, ?>[] catchStatement) {
            this.catchStatement = catchStatement;
            return this;
        }

        /**
         * 设置finally语句块
         * 
         * @param finallyStatement finally语句块
         * @return 构造器自身
         */
        public Builder<T> finallyStatement(Consumer<T> finallyStatement) {
            this.finallyStatement = finallyStatement;
            return this;
        }

        /**
         * 构造
         * 
         * @return try-catch-finally包装器
         */
        public TryWrapper<T> build() {
            return new TryWrapper<>(tryStatement, catchStatement, finallyStatement);
        }
    }
}
