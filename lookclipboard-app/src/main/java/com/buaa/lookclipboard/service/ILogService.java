/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-06-04 01:00:15
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-04 16:12:30
 * 
 * @Description: 日志服务接口
 */
package com.buaa.lookclipboard.service;

import org.apache.logging.log4j.Level;

/**
 * 日志服务接口
 */
public interface ILogService extends IAppService {
    /**
     * 追踪
     * 
     * @param message 消息
     */
    void trace(String message);

    /**
     * 追踪
     * 
     * @param message   消息
     * @param throwable 异常
     */
    void trace(String message, Throwable throwable);

    /**
     * 追踪
     * 
     * @param message 消息
     * @param params  参数
     */
    void trace(String message, Object... params);

    /**
     * 调试
     * 
     * @param message 消息
     */
    void debug(String message);

    /**
     * 调试
     * 
     * @param message   消息
     * @param throwable 异常
     */
    void debug(String message, Throwable throwable);

    /**
     * 调试
     * 
     * @param message 消息
     * @param params  参数
     */
    void debug(String message, Object... params);

    /**
     * 信息
     * 
     * @param message 消息
     */
    void info(String message);

    /**
     * 信息
     * 
     * @param message   消息
     * @param throwable 异常
     */
    void info(String message, Throwable throwable);

    /**
     * 信息
     * 
     * @param message 消息
     * @param params  参数
     */
    void info(String message, Object... params);

    /**
     * 警告
     * 
     * @param message 消息
     */
    void warn(String message);

    /**
     * 警告
     * 
     * @param message   消息
     * @param throwable 异常
     */
    void warn(String message, Throwable throwable);

    /**
     * 警告
     * 
     * @param message 消息
     * @param params  参数
     */
    void warn(String message, Object... params);

    /**
     * 错误
     * 
     * @param message 消息
     */
    void error(String message);

    /**
     * 错误
     * 
     * @param message   消息
     * @param throwable 异常
     */
    void error(String message, Throwable throwable);

    /**
     * 错误
     * 
     * @param message 消息
     * @param params  参数
     */
    void error(String message, Object... params);

    /**
     * 致命
     * 
     * @param message 消息
     */
    void fatal(String message);

    /**
     * 致命
     * 
     * @param message   消息
     * @param throwable 异常
     */
    void fatal(String message, Throwable throwable);

    /**
     * 致命
     * 
     * @param message 消息
     * @param params  参数
     */
    void fatal(String message, Object... params);

    /**
     * 记录
     * 
     * @param level   级别
     * @param message 消息
     */
    void log(Level level, String message);

    /**
     * 记录
     * 
     * @param level     级别
     * @param message   消息
     * @param throwable 异常
     */
    void log(Level level, String message, Throwable throwable);
    
    /**
     * 记录
     * 
     * @param level   级别
     * @param message 消息
     * @param params  参数
     */
    void log(Level level, String message, Object... params);
}
