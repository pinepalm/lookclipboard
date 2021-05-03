/*
 * @Author: Zhe Chen
 * @Date: 2021-05-03 11:54:44
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 13:44:29
 * @Description: 记录查询条件测试
 */
package com.buaa.lookclipboard.model;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import com.buaa.lookclipboard.util.JsonUtil;

import org.junit.Test;

/**
 * 记录查询条件测试
 */
public class RecordQueryConditionTest {
    /**
     * 打印Sql语句测试
     */
    @Test
    public void printSqlTest() {
        RecordQueryCondition condition = new RecordQueryCondition();
        condition.setContentPattern("%test%");
        String[][] dataFormats = {{"text/plain"},{"text/foo"}};
        condition.setDataFormats(dataFormats);
        condition.setEndTime(LocalDateTime.now());
        condition.setIsPinned(true);
        condition.setPageIndex(1);
        condition.setPageSize(10);
        condition.setStartTime(null);

        System.out.println(condition);
        
        String json = JsonUtil.stringify(condition);
        //String json = "{\"dataFormats\":null,\"startTime\":null,\"endTime\":\"2021-05-03 12:23:16.333\",\"pageSize\":10,\"pageIndex\":null}";
        System.out.println(json);
        RecordQueryCondition conditionByJson = JsonUtil.parse(json, RecordQueryCondition.class);
        System.out.println(conditionByJson);

        assertTrue(true);
    }
}