/*
 * @Author: Zhe Chen
 * @Date: 2021-05-01 23:47:22
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 01:31:38
 * @Description: 记录数据访问接口
 */
package com.buaa.lookclipboard.dao;

import java.util.List;

import com.buaa.lookclipboard.model.Record;
import com.buaa.lookclipboard.model.RecordQueryCondition;

/**
 * 记录数据访问接口
 */
public interface IRecordDao {
    /**
     * 若记录表不存在, 则创建记录表
     */
    void createIfNotExists();
    
    /**
     * 添加记录
     * 
     * @param record 记录
     */
    void add(Record record);

    /**
     * 更新记录
     * 
     * @param record 记录
     */
    void update(Record record);

    /**
     * 删除记录
     * 
     * @param id 记录ID
     */
    void delete(String id);

    /**
     * 通过ID获取指定记录
     * 
     * @param id 记录ID
     * @return 记录
     */
    Record getById(String id);

    /**
     * 通过记录查询条件获取记录列表
     * 
     * @param condition 记录查询条件
     * @return 记录列表
     */
    List<Record> getByCondition(RecordQueryCondition condition);

    /**
     * 通过记录查询条件获取记录数
     * 
     * @param condition 记录查询条件
     * @return 记录数
     */
    int getCountByCondition(RecordQueryCondition condition);
}
