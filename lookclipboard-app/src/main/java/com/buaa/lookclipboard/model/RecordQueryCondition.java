/*
 * @Author: Zhe Chen
 * @Date: 2021-05-03 00:51:29
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 13:41:53
 * @Description: 记录查询条件
 */
package com.buaa.lookclipboard.model;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import com.buaa.lookclipboard.util.DataFormatUtil;
import com.buaa.lookclipboard.util.JsonUtil;
import com.buaa.lookclipboard.util.LocalDateTimeUtil;
import com.buaa.lookclipboard.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import javafx.scene.input.DataFormat;

/**
 * 记录查询条件
 */
public class RecordQueryCondition {
    private String[][] dataFormats;
    private String contentPattern;
    @JsonFormat(pattern = LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)
    private LocalDateTime startTime;
    @JsonFormat(pattern = LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS)
    private LocalDateTime endTime;
    private Boolean isPinned;
    private Integer pageSize;
    private Integer pageIndex;

    /**
     * 获取数据类型JSON字符串数组
     * @return 数据类型JSON字符串数组
     */
    public String[][] getDataFormats() {
        return dataFormats;
    }

    /**
     * 设置数据类型JSON字符串数组
     * @param dataFormats 数据类型JSON字符串数组
     */
    public void setDataFormats(String[][] dataFormats) {
        this.dataFormats = dataFormats;
    }

    /**
     * 获取内容匹配模式字符串
     * @return 内容匹配模式字符串
     */
    public String getContentPattern() {
        return contentPattern;
    }

    /**
     * 设置内容匹配模式字符串
     * @param contentPattern 内容匹配模式字符串
     */
    public void setContentPattern(String contentPattern) {
        this.contentPattern = contentPattern;
    }

    /**
     * 获取开始时间(创建时间)
     * @return 开始时间(创建时间)
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间(创建时间)
     * @param startTime 开始时间(创建时间)
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间(创建时间)
     * @return 结束时间(创建时间)
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间(创建时间)
     * @param endTime 结束时间(创建时间)
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取是否固定
     * @return 是否固定
     */
    public Boolean getIsPinned() {
        return isPinned;
    }

    /**
     * 设置是否固定
     * @param isPinned 是否固定
     */
    public void setIsPinned(Boolean isPinned) {
        this.isPinned = isPinned;
    }

    /**
     * 获取每页记录数
     * @return 每页记录数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     * @param pageSize 每页记录数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取页码[1, ...]
     * @return 页码[1, ...]
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置页码[1, ...]
     * @param pageIndex 页码[1, ...]
     */
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 将记录查询条件转为Sql字符串
     * <blockquote>
     * <pre>
     * [where (...)] [limit ? offest ?] order by createdTime desc
     * </pre>
     * </blockquote>
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" and ");

        String[][] dataFormats = getDataFormats();
        if (dataFormats != null) {
            StringJoiner dataFormatsJoiner = new StringJoiner(" or ");

            for (String[] dataFormatString : dataFormats) {
                DataFormat dataFormat = DataFormatUtil.fromJSON(JsonUtil.stringify(dataFormatString));
                if (dataFormat != null) {
                    dataFormatsJoiner.add(String.format("dataFormat = %s",
                            ObjectUtil.asSqlString(DataFormatUtil.toJSON(dataFormat))));
                }
            }

            joiner.add(String.format("(%s)", dataFormatsJoiner.toString()));
        }

        String contentPattern = getContentPattern();
        if (contentPattern != null) {
            joiner.add(String.format("(content like %s)", ObjectUtil.asSqlString(contentPattern)));
        }

        LocalDateTime startTime = getStartTime();
        if (startTime != null) {
            joiner.add(String.format("(createdTime >= %s)", ObjectUtil
                    .asSqlString(LocalDateTimeUtil.format(startTime, LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS))));
        }

        LocalDateTime endTime = getEndTime();
        if (endTime != null) {
            joiner.add(String.format("(createdTime <= %s)", ObjectUtil
                    .asSqlString(LocalDateTimeUtil.format(endTime, LocalDateTimeUtil.yyyy_MM_dd_HH_mm_ss_SSS))));
        }

        Boolean isPinned = getIsPinned();
        if (isPinned != null) {
            joiner.add(String.format("(isPinned = %d)", isPinned ? 1 : 0));
        }

        String baseSql = joiner.toString();
        if (!baseSql.isEmpty()) {
            baseSql = String.format("where (%s)", baseSql);
        }

        Integer pageSize = getPageSize();
        Integer pageIndex = getPageIndex();
        if (pageSize != null && pageIndex != null) {
            int newPageSize = Math.max(pageSize, 1);
            int newPageIndex = Math.max(pageIndex, 1);
            baseSql = String.format("%s limit %d offest %d", baseSql, newPageSize, newPageIndex - 1);
        }

        baseSql = String.format("%s order by createdTime desc", baseSql);
        return baseSql;
    }
}