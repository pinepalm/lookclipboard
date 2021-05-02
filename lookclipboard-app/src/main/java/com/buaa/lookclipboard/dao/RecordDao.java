/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 12:52:04
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 18:11:54
 * @Description: 记录数据访问类
 */
package com.buaa.lookclipboard.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.buaa.lookclipboard.model.Record;
import com.buaa.lookclipboard.util.DataFormatUtil;
import com.buaa.lookclipboard.util.LocalDateTimeUtil;
import com.buaa.lookclipboard.util.ObjectUtil;

import javafx.scene.input.DataFormat;

/**
 * 记录数据访问类
 */
public final class RecordDao implements IRecordDao {
    /**
     * 记录数据访问类实例
     */
    public final static RecordDao instance = new RecordDao();

    private int executeUpdate(String sql) {
        int rowsCount = 0;

        try {
            Statement statement = DataAccessCenter.createStatement();
            rowsCount = statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowsCount;
    }

    private List<Record> executeQuery(String sql) {
        List<Record> recordList = new ArrayList<>();

        try {
            Statement statement = DataAccessCenter.createStatement();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Record record) {
        executeUpdate(String.format(
                "insert into records (id, dataFormat, createdTime, modifiedTime, content, isPinned) values (%s, %s, %s, %s, %s, %d)",
                ObjectUtil.asSqlString(record.getID()),
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
        executeUpdate(String.format(
                "update records set dataFormat = %s, createdTime = %s, modifiedTime = %s, content = %s, isPinned = %d where id = %s",
                ObjectUtil.asSqlString(DataFormatUtil.toJSON(record.getDataFormat())),
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
        executeUpdate(String.format("delete from records where id = %s", ObjectUtil.asSqlString(id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Record getById(String id) {
        List<Record> recordList = executeQuery(
                String.format("select * from records where id = %s", ObjectUtil.asSqlString(id)));
        return !recordList.isEmpty() ? recordList.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record> getByLikePattern(String pattern) {
        return executeQuery(String.format("select * from records where content like %s order by createdTime desc",
                ObjectUtil.asSqlString(pattern)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record> getByDataFormat(DataFormat dataFormat) {
        return executeQuery(String.format("select * from records where dataFormat = %s order by createdTime desc",
                ObjectUtil.asSqlString(DataFormatUtil.toJSON(dataFormat))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record> getAll() {
        return executeQuery("select * from records order by createdTime desc");
    }
}