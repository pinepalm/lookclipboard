/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-30 10:45:59
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-11 14:50:54
 * 
 * @Description: 记录标签
 */
package com.buaa.lookclipboard.model;

/**
 * 记录标签
 */
public class RecordTag implements IRecordTag {
    private final String id;
    private final String name;

    /**
     * 指定ID和名称进行构造
     * 
     * @param id   ID
     * @param name 名称
     */
    public RecordTag(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
}
