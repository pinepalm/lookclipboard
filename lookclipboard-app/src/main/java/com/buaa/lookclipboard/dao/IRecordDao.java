/*
 * @Author: Zhe Chen
 * @Date: 2021-05-01 23:47:22
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 18:08:08
 * @Description: 记录数据访问接口
 */
package com.buaa.lookclipboard.dao;

import java.util.List;

import com.buaa.lookclipboard.model.Record;

import javafx.scene.input.DataFormat;

/**
 * 记录数据访问接口
 */
public interface IRecordDao {
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
     * 通过Like匹配模式获取记录列表
     * 
     * @return 记录列表
     */
    List<Record> getByLikePattern(String pattern);

    /**
     * 通过数据类型获取记录列表
     * 
     * @return 记录列表
     */
    List<Record> getByDataFormat(DataFormat dataFormat);

    /**
     * 获取全部记录
     * 
     * @return 全部记录
     */
    List<Record> getAll();
}
