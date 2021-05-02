/*
 * @Author: Zhe Chen
 * @Date: 2021-05-01 23:57:34
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 12:18:58
 * @Description: 数据访问中心
 */
package com.buaa.lookclipboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import com.buaa.lookclipboard.AppConfig;

/**
 * 数据访问中心
 */
public final class DataAccessCenter {
    private final static String JDBC_DRIVER = "org.sqlite.JDBC";
    private final static String JDBC_SCHEME = "jdbc:sqlite";
    private final static String DATABASE_PATH = String.format("%s/record.db", AppConfig.instance.getDataFolder().getAbsolutePath());
    private final static String DATABASE_URL = String.format("%s://%s", JDBC_SCHEME, DATABASE_PATH);
    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection connection;

    /**
     * 打开数据访问中心
     * 
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    public static void open() throws SQLException, SQLTimeoutException {
        if (connection != null) {
            return;
        }

        connection = DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * 创建语句对象
     * 
     * @return 语句对象
     * @throws SQLException
     */
    public static Statement createStatement() throws SQLException {
        if (connection == null) {
            return null;
        }

        return connection.createStatement();
    }

    /**
     * 关闭数据访问中心
     * 
     * @throws SQLException
     */
    public static void close() throws SQLException {
        if (connection == null) {
            return;
        }

        connection.close();
        connection = null;
    }

    private DataAccessCenter() {

    }
}
