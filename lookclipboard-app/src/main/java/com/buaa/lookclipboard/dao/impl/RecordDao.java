/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 12:52:04
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 23:37:40
 * @Description: 记录数据访问类
 */
package com.buaa.lookclipboard.dao.impl;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.buaa.commons.foundation.Lazy;
import com.buaa.lookclipboard.dao.DataAccessCenter;
import com.buaa.lookclipboard.dao.IRecordDao;
import com.buaa.lookclipboard.model.Record;
import com.buaa.lookclipboard.model.RecordQueryCondition;
import com.buaa.lookclipboard.util.DataFormatUtil;
import com.buaa.lookclipboard.util.LocalDateTimeUtil;
import com.buaa.lookclipboard.util.ObjectUtil;

/**
 * 记录数据访问类
 */
public final class RecordDao implements IRecordDao {
    private final static Lazy<RecordDao> instance = new Lazy<>(() -> new RecordDao());

    /**
     * 获取记录数据访问类实例
     * 
     * @return 记录数据访问类实例
     */
    public static RecordDao getInstance() {
        return instance.getValue();
    }

    private RecordDao() {
        
    }

    private int manipulateRecords(String sql) {
        int rowsCount = 0;

        try {
            Statement statement = DataAccessCenter.getInstance().createStatement();
            rowsCount = statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowsCount;
    }

    private List<Record> queryRecords(String sql) {
        List<Record> recordList = new ArrayList<>();

        try {
            Statement statement = DataAccessCenter.getInstance().createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Record tempRecord = new Record();
                tempRecord.setID(rs.getString("id"));
                tempRecord.setDataFormat(DataFormatUtil.fromJSON(rs.getString("dataFormat")));
                Timestamp createdTime = rs.getTimestamp("createdTime");
                tempRecord.setCreatedTime(createdTime != null ? createdTime.toLocalDateTime() : null);
                Timestamp modifiedTime = rs.getTimestamp("modifiedTime");
                tempRecord.setModifiedTime(modifiedTime != null ? modifiedTime.toLocalDateTime() : null);
                tempRecord.setContent(rs.getString("content"));
                tempRecord.setIsPinned(rs.getBoolean("isPinned"));

                recordList.add(tempRecord);
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recordList;
    }

    private int queryRecordsCount(String sql) {
        int rowsCount = 0;

        try {
            Statement statement = DataAccessCenter.getInstance().createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                rowsCount = rs.getInt(1);
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowsCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createIfNotExists() {
        String baseSql = "create table if not exists records (id text primary key not null, dataFormat text not null, createdTime timestamp, modifiedTime timestamp, content text, isPinned boolean not null)";
        manipulateRecords(baseSql);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Record record) {
        String baseSql = "insert into records (id, dataFormat, createdTime, modifiedTime, content, isPinned) values (%s, %s, %s, %s, %s, %d)";
        manipulateRecords(String.format(baseSql, ObjectUtil.asSqlString(record.getID()),
                ObjectUtil.asSqlString(DataFormatUtil.toJSON(record.getDataFormat())),
                ObjectUtil.asSqlString(
                        LocalDateTimeUtil.format(record.getCreatedTime(), LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)),
                ObjectUtil.asSqlString(
                        LocalDateTimeUtil.format(record.getModifiedTime(), LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)),
                ObjectUtil.asSqlString(record.getContent()), record.getIsPinned() ? 1 : 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Record record) {
        String baseSql = "update records set dataFormat = %s, createdTime = %s, modifiedTime = %s, content = %s, isPinned = %d where id = %s";
        manipulateRecords(String.format(baseSql, ObjectUtil.asSqlString(DataFormatUtil.toJSON(record.getDataFormat())),
                ObjectUtil.asSqlString(
                        LocalDateTimeUtil.format(record.getCreatedTime(), LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)),
                ObjectUtil.asSqlString(
                        LocalDateTimeUtil.format(record.getModifiedTime(), LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)),
                ObjectUtil.asSqlString(record.getContent()), record.getIsPinned() ? 1 : 0,
                ObjectUtil.asSqlString(record.getID())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        String baseSql = "delete from records where id = %s";
        manipulateRecords(String.format(baseSql, ObjectUtil.asSqlString(id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Record getById(String id) {
        String baseSql = "select * from records where id = %s";
        List<Record> recordList = queryRecords(String.format(baseSql, ObjectUtil.asSqlString(id)));
        return !recordList.isEmpty() ? recordList.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record> getByCondition(RecordQueryCondition condition) {
        String baseSql = "select * from records %s";
        return queryRecords(String.format(baseSql, condition != null ? condition : ""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCountByCondition(RecordQueryCondition condition) {
        String baseSql = "select count(*) from records %s";
        return queryRecordsCount(String.format(baseSql, condition != null ? condition : ""));
    }
}