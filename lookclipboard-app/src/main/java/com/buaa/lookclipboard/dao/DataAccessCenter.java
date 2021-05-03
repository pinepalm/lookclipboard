/*
 * @Author: Zhe Chen
 * @Date: 2021-05-01 23:57:34
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 23:37:00
 * @Description: 数据访问中心
 */
package com.buaa.lookclipboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import com.buaa.commons.foundation.IClosable;
import com.buaa.commons.foundation.Lazy;
import com.buaa.lookclipboard.AppConfig;

/**
 * 数据访问中心
 */
public final class DataAccessCenter implements IClosable {
    private final static Lazy<DataAccessCenter> instance = new Lazy<>(() -> new DataAccessCenter());

    /**
     * 获取数据访问中心实例
     * 
     * @return 数据访问中心实例
     */
    public static DataAccessCenter getInstance() {
        return instance.getValue();
    }

    private final String JDBC_DRIVER = "org.sqlite.JDBC";
    private final String JDBC_SCHEME = "jdbc:sqlite";
    private final String DATABASE_PATH = String.format("%s/record.db",
            AppConfig.getInstance().getDataFolder().getAbsolutePath());
    private final String DATABASE_URL = String.format("%s://%s", JDBC_SCHEME, DATABASE_PATH);

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
     * @throws SQLException
     * @throws SQLTimeoutException
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
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException {
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
    public void close() throws SQLException {
        if (connection == null) {
            return;
        }

        connection.close();
        connection = null;
    }
}
