/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-06-04 01:01:35
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-04 16:11:31
 * 
 * @Description: 日志服务
 */
package com.buaa.lookclipboard.service.impl;

import com.buaa.commons.foundation.Lazy;
import com.buaa.lookclipboard.AppConfig;
import com.buaa.lookclipboard.service.ILogService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.apache.logging.log4j.util.StackLocatorUtil;

/**
 * 日志服务
 */
public final class LogService implements ILogService {
    private final static String LOG4J_CONTEXT_SELECTOR = "Log4jContextSelector";
    private final static String APP_LOG_PROPERTY = "app.log";

    private final static Lazy<LogService> instance = new Lazy<>(() -> new LogService());

    /**
     * 获取日志服务实例
     * 
     * @return 日志服务实例
     */
    public static LogService getInstance() {
        return instance.getValue();
    }

    private LogService() {
        System.setProperty(LOG4J_CONTEXT_SELECTOR, AsyncLoggerContextSelector.class.getName());
        System.setProperty(APP_LOG_PROPERTY, AppConfig.getInstance().getAppLogsFolder().getAbsolutePath());
    }

    private Logger logger() {
        return LogManager.getLogger(StackLocatorUtil.getCallerClass(3));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(String message) {
        logger().trace(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(String message, Throwable throwable) {
        logger().trace(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(String message, Object... params) {
        logger().trace(message, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String message) {
        logger().debug(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String message, Throwable throwable) {
        logger().debug(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String message, Object... params) {
        logger().debug(message, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String message) {
        logger().info(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String message, Throwable throwable) {
        logger().info(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String message, Object... params) {
        logger().info(message, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String message) {
        logger().warn(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String message, Throwable throwable) {
        logger().warn(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String message, Object... params) {
        logger().warn(message, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(String message) {
        logger().error(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(String message, Throwable throwable) {
        logger().error(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(String message, Object... params) {
        logger().error(message, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatal(String message) {
        logger().fatal(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatal(String message, Throwable throwable) {
        logger().fatal(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatal(String message, Object... params) {
        logger().fatal(message, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(Level level, String message) {
        logger().log(level, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(Level level, String message, Throwable throwable) {
        logger().log(level, message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(Level level, String message, Object... params) {
        logger().log(level, message, params);
    }
}
