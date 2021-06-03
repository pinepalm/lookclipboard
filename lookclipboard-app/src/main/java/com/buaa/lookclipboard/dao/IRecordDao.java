/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-01 23:47:22
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 15:58:49
 * 
 * @Description: 记录数据访问接口
 */
package com.buaa.lookclipboard.dao;

import java.sql.SQLException;
import java.util.List;

import com.buaa.lookclipboard.model.Record;
import com.buaa.lookclipboard.model.RecordQueryCondition;

/**
 * 记录数据访问接口
 */
public interface IRecordDao extends IDataAccessObject {
    /**
     * 若记录表不存在, 则创建记录表
     * 
     * @throws SQLException 可能抛出的SQL异常
     */
    void createIfNotExists() throws SQLException;

    /**
     * 添加记录
     * 
     * @param record 记录
     * @throws SQLException 可能抛出的SQL异常
     */
    void add(Record record) throws SQLException;

    /**
     * 更新记录
     * 
     * @param record 记录
     * @throws SQLException 可能抛出的SQL异常
     */
    void update(Record record) throws SQLException;

    /**
     * 删除记录
     * 
     * @param id 记录ID
     * @throws SQLException 可能抛出的SQL异常
     */
    void delete(String id) throws SQLException;

    /**
     * 删除批量记录
     * 
     * @param ids 记录ID列表
     * @throws SQLException 可能抛出的SQL异常
     */
    void deleteBatch(String... ids) throws SQLException;

    /**
     * 删除全部记录
     * @throws SQLException 可能抛出的SQL异常
     */
    void deleteAll() throws SQLException;

    /**
     * 通过ID获取指定记录
     * 
     * @param id 记录ID
     * @return 记录
     * @throws SQLException 可能抛出的SQL异常
     */
    Record getById(String id) throws SQLException;

    /**
     * 通过ID列表获取记录列表
     * 
     * @param ids 记录ID列表
     * @return 记录列表
     * @throws SQLException 可能抛出的SQL异常
     */
    List<Record> getByIdList(String... ids) throws SQLException;

    /**
     * 通过记录查询条件获取记录列表
     * 
     * @param condition 记录查询条件
     * @return 记录列表
     * @throws SQLException 可能抛出的SQL异常
     */
    List<Record> getByCondition(RecordQueryCondition condition) throws SQLException;

    /**
     * 通过记录查询条件获取记录数
     * 
     * @param condition 记录查询条件
     * @return 记录数
     * @throws SQLException 可能抛出的SQL异常
     */
    int getCountByCondition(RecordQueryCondition condition) throws SQLException;
}
