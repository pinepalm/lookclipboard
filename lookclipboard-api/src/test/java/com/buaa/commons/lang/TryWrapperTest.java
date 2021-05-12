/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-11 18:25:16
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-12 17:02:31
 * 
 * @Description: TryWrapperTest
 */
package com.buaa.commons.lang;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import org.junit.Test;

/**
 * TryWrapper测试
 */
public class TryWrapperTest {
    @Test
    public void print() {
        BiConsumer<IllegalAccessException, ITryContext> ae = (e, context) -> {
            System.out.println("IllegalAccessException" + e.getMessage());
        };
        List<ExceptionHandler<?, ?>> handlers =
                Arrays.asList(new ExceptionHandler<>((e, context) -> {
                    System.out.println(e.getMessage());
                }, Exception.class), new ExceptionHandler<>(ae, IllegalAccessException.class));
        TryWrapper<ITryContext> tryWrapper = new TryWrapper<>((context) -> {
            throw new IllegalAccessException("233");
        }, handlers, (context) -> {
            System.out.println("test");
        });
        tryWrapper.invoke();
    }
}
