/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-03 00:51:29
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-06 23:37:30
 * 
 * @Description: 记录查询条件
 */
package com.buaa.lookclipboard.model;

import java.time.LocalDateTime;

import com.buaa.lookclipboard.util.LocalDateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 记录查询条件
 */
public class RecordQueryCondition {
    private String[][] dataFormats;
    private String queryContent;
    @JsonFormat(pattern = LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)
    private LocalDateTime startTime;
    @JsonFormat(pattern = LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)
    private LocalDateTime endTime;
    private Boolean isPinned;
    private Integer pageSize;
    private Integer pageIndex;

    /**
     * 获取数据类型JSON字符串数组
     * 
     * @return 数据类型JSON字符串数组
     */
    public String[][] getDataFormats() {
        return dataFormats;
    }

    /**
     * 设置数据类型JSON字符串数组
     * 
     * @param dataFormats 数据类型JSON字符串数组
     */
    public void setDataFormats(String[][] dataFormats) {
        this.dataFormats = dataFormats;
    }

    /**
     * 获取查询内容
     * 
     * @return 查询内容
     */
    public String getQueryContent() {
        return queryContent;
    }

    /**
     * 设置查询内容
     * 
     * @param queryContent 查询内容
     */
    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }

    /**
     * 获取开始时间(创建时间)
     * 
     * @return 开始时间(创建时间)
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间(创建时间)
     * 
     * @param startTime 开始时间(创建时间)
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间(创建时间)
     * 
     * @return 结束时间(创建时间)
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间(创建时间)
     * 
     * @param endTime 结束时间(创建时间)
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取是否固定
     * 
     * @return 是否固定
     */
    public Boolean getIsPinned() {
        return isPinned;
    }

    /**
     * 设置是否固定
     * 
     * @param isPinned 是否固定
     */
    public void setIsPinned(Boolean isPinned) {
        this.isPinned = isPinned;
    }

    /**
     * 获取每页记录数
     * 
     * @return 每页记录数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     * 
     * @param pageSize 每页记录数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取页码[1, ...]
     * 
     * @return 页码[1, ...]
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置页码[1, ...]
     * 
     * @param pageIndex 页码[1, ...]
     */
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
