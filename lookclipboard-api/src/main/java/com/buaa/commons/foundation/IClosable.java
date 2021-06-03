/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-18 14:12:44
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:34:21
 * 
 * @Description: 可关闭接口
 */
package com.buaa.commons.foundation;

/**
 * 可关闭接口
 */
public interface IClosable {
    /**
     * 关闭
     * 
     * @throws Exception 可能抛出的异常
     */
    void close() throws Exception;
}
