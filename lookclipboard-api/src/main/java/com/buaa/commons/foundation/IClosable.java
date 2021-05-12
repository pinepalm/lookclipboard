/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-18 14:12:44
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-03 23:41:38
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
     * @throws Exception
     */
    void close() throws Exception;
}
