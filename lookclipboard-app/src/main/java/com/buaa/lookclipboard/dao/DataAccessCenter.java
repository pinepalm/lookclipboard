/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-01 23:57:34
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:59:03
 * 
 * @Description: 数据访问中心
 */
package com.buaa.lookclipboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import com.buaa.commons.foundation.IClosable;
import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.util.StringUtil;
import com.buaa.lookclipboard.AppConfig;
import org.apache.commons.io.FileUtils;

/**
 * 数据访问中心
 */
public final class DataAccessCenter implements IClosable {
    private final static String JDBC_DRIVER = "org.sqlite.JDBC";
    private final static String JDBC_SCHEME = "jdbc:sqlite";
    
    private final static String DATABASE_NAME = "record.db";
    private final static String DATABASE_PATH = FileUtils.getFile(AppConfig.getInstance().getAppDataFolder(), DATABASE_NAME).getAbsolutePath();
    private final static String DATABASE_URL = StringUtil.interpolate(
            "${JDBC_SCHEME}://${DATABASE_PATH}", new Object[][] 
            {
                {"JDBC_SCHEME", JDBC_SCHEME}, 
                {"DATABASE_PATH", DATABASE_PATH}
            });

    private final static Lazy<DataAccessCenter> instance = new Lazy<>(() -> new DataAccessCenter());

    /**
     * 获取数据访问中心实例
     * 
     * @return 数据访问中心实例
     */
    public static DataAccessCenter getInstance() {
        return instance.getValue();
    }

    private Connection connection;

    private DataAccessCenter() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开数据访问中心
     * 
     * @throws SQLException        可能抛出的SQL异常
     * @throws SQLTimeoutException 可能抛出的SQL执行超时异常
     */
    public void open() throws SQLException, SQLTimeoutException {
        if (connection != null) {
            return;
        }

        connection = DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * 创建语句对象
     * 
     * @return 语句对象
     * @throws SQLException 可能抛出的SQL异常
     */
    public Statement createStatement() throws SQLException {
        if (connection == null) {
            return null;
        }

        return connection.createStatement();
    }

    /**
     * 创建预编译语句对象
     * 
     * @param sql Sql语句
     * @return 预编译语句对象
     * @throws SQLException 可能抛出的SQL异常
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (connection == null) {
            return null;
        }

        return connection.prepareStatement(sql);
    }

    /**
     * 关闭数据访问中心
     * 
     * @throws SQLException 可能抛出的SQL异常
     */
    public void close() throws SQLException {
        if (connection == null) {
            return;
        }

        connection.close();
        connection = null;
    }
}
