/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 18:25:16
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-13 15:54:17
 * 
 * @Description: TryWrapperTest
 */
package com.buaa.commons.lang;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.function.BiConsumer;
import org.junit.Test;

/**
 * TryWrapper测试
 */
public class TryWrapperTest {
    @Test
    public void print() {
        BiConsumer<IOException, ITryContext> ae = (e, context) -> {
            System.out.println("IOException" + e.getMessage());
        };
        ExceptionHandler<?, ?>[] handlers = new ExceptionHandler<?, ?>[] {
                new ExceptionHandler<>(ae, FileNotFoundException.class, FileSystemException.class),
                new ExceptionHandler<RuntimeException, ITryContext>((e, context) -> {
                    System.out.println(e.getMessage());
                }, RuntimeException.class)};
        TryWrapper<ITryContext> tryWrapper = new TryWrapper<>((context) -> {
            throw new Exception("233");
        }, handlers, (context) -> {
            System.out.println("test");
        });
        tryWrapper.invoke();
    }
}
