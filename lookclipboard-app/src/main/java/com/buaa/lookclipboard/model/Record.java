/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 12:48:03
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 17:21:50
 * @Description: 记录
 */
package com.buaa.lookclipboard.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.buaa.commons.foundation.Lazy;
import com.buaa.lookclipboard.util.LocalDateTimeUtil;
import com.buaa.lookclipboard.util.Md5Util;
import com.fasterxml.jackson.annotation.JsonFormat;

import javafx.scene.input.DataFormat;

/**
 * 记录
 */
public class Record implements IRecord {
    private static Random random = new Random();

    /**
     * 根据当前时间创建记录
     * 
     * @param dataFormat 数据类型
     * @return 记录
     */
    public static Record createNow(DataFormat dataFormat) {
        LocalDateTime createdTime = LocalDateTime.now(ZoneOffset.UTC);
        String id = Md5Util.md5(createdTime.toString() + random.nextInt());
        Record record = new Record();
        record.setID(id);
        record.setDataFormat(dataFormat);
        record.setCreatedTime(createdTime);
        
        return record;
    }

    private String id;
    private DataFormat dataFormat;
    @JsonFormat(pattern = LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)
    private LocalDateTime createdTime;
    @JsonFormat(pattern = LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)
    private LocalDateTime modifiedTime;
    private String content;
    private boolean isPinned;

    private final List<IRecordTag> tags = new ArrayList<>();;
    private final Lazy<IRecord> readonlyRecord = new Lazy<>(() -> new ReadOnlyRecord());

    public Record() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * 设置ID
     * 
     * @param id 记录ID
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFormat getDataFormat() {
        return dataFormat;
    }

    /**
     * 设置数据类型
     * 
     * @param dataFormat 数据类型
     */
    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createdTime 创建时间
     */
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置修改时间
     * 
     * @param modifiedTime 修改时间
     */
    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     * 
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getIsPinned() {
        return isPinned;
    }

    /**
     * 设置是否固定
     * 
     * @param isPinned 是否固定
     */
    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IRecordTag> getTags() {
        return tags;
    }

    /**
     * 转为只读副本
     * 
     * @return 只读副本
     */
    public IRecord asReadOnly() {
        return readonlyRecord.getValue();
    }

    private class ReadOnlyRecord implements IRecord {
        private final List<IRecordTag> readonlyTags;

        private ReadOnlyRecord() {
            readonlyTags = Collections.unmodifiableList(Record.this.getTags());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getID() {
            return Record.this.getID();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public DataFormat getDataFormat() {
            return Record.this.getDataFormat();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LocalDateTime getCreatedTime() {
            return Record.this.getCreatedTime();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LocalDateTime getModifiedTime() {
            return Record.this.getModifiedTime();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getContent() {
            return Record.this.getContent();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getIsPinned() {
            return Record.this.getIsPinned();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<IRecordTag> getTags() {
            return readonlyTags;
        }
    }
}