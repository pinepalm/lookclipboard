/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 18:25:16
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 14:46:58
 * 
 * @Description: TryWrapperTest
 */
package com.buaa.commons.lang;

import static org.junit.Assert.assertEquals;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import org.junit.Test;

/**
 * TryWrapper测试
 */
public class TryWrapperTest {
    /**
     * 测试Try
     */
    @Test
    public void testTry() {
        TryWrapper<TestTryContext> tryWrapper = new TryWrapper<>((context) -> {
            context.message = "Try";
        });

        TestTryContext context = new TestTryContext();
        tryWrapper.invoke(context);
        assertEquals(context.message, "Try");
    }

    /**
     * 测试Catch
     */
    @Test
    public void testCatch() {
        ExceptionHandler<?, ?>[] handlers = new ExceptionHandler<?, ?>[] {
            new ExceptionHandler<IOException, TestTryContext>((e, context)-> {
                context.message = "IOException";
            }, FileNotFoundException.class, FileSystemException.class),
            new ExceptionHandler<RuntimeException, TestTryContext>((e, context) -> {
                context.message = "RuntimeException";
            }, RuntimeException.class)
        };
        TryWrapper<TestTryContext> tryWrapper = new TryWrapper<>((context) -> {
            throw new RuntimeException();
        }, handlers);

        TestTryContext context = new TestTryContext();
        tryWrapper.invoke(context);
        assertEquals(context.message, "RuntimeException");
    }

    /**
     * 测试Finally
     */
    @Test
    public void testFinally() {
        ExceptionHandler<?, ?>[] handlers = new ExceptionHandler<?, ?>[] {
            new ExceptionHandler<IOException, TestTryContext>((e, context)-> {
                context.message = "IOException";
            }, FileNotFoundException.class, FileSystemException.class),
            new ExceptionHandler<RuntimeException, TestTryContext>((e, context) -> {
                context.message = "RuntimeException";
            }, RuntimeException.class)
        };
        TryWrapper<TestTryContext> tryWrapper = new TryWrapper<>((context) -> {
            throw new RuntimeException();
        }, handlers, (context) -> {
            context.message = "Finally";
        });

        TestTryContext context = new TestTryContext();
        tryWrapper.invoke(context);
        assertEquals(context.message, "Finally");
    }

    public class TestTryContext implements ITryContext {
        public String message;
    }
}
