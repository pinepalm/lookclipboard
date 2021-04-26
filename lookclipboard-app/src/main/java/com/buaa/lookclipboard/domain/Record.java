/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 12:48:03
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-23 16:38:39
 * @Description: file content
 */
package com.buaa.lookclipboard.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

import com.buaa.lookclipboard.util.Md5Util;
import com.fasterxml.jackson.annotation.JsonFormat;

import javafx.scene.input.DataFormat;

public class Record implements IRecord {
    public static Random random = new Random();

    public static Record createNow(DataFormat dataFormat) {
        LocalDateTime createdTime = LocalDateTime.now(ZoneOffset.UTC);
        String id = Md5Util.md5(createdTime.toString() + random.nextInt());
        return new Record(id, dataFormat, createdTime);
    }

    private String id;
    private DataFormat dataFormat;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime modifiedTime;
    private String info;
    private boolean isPinned;

    public Record(String id, DataFormat dataFormat, LocalDateTime createdTime) {
        this.id = id;
        this.dataFormat = dataFormat;
        this.createdTime = createdTime;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }
}