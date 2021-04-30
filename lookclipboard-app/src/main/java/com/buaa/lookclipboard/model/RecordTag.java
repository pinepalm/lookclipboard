/*
 * @Author: Zhe Chen
 * @Date: 2021-04-30 10:45:59
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 14:58:17
 * @Description: 记录标签
 */
package com.buaa.lookclipboard.model;

/**
 * 记录标签
 */
public class RecordTag implements IRecordTag {
    private final String id;
    private final String name;

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