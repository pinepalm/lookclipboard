/*
 * @Author: Zhe Chen
 * @Date: 2021-05-03 22:30:56
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 22:40:23
 * @Description: 剪贴板服务接口
 */
package com.buaa.lookclipboard.service;

/**
 * 剪贴板服务接口
 */
public interface IClipboardService extends IAppService {
    /**
     * 复制记录内容
     * 
     * @param id 记录ID
     * @return 操作结果JSON字符串
     */
    String copy(String id);

    /**
     * 删除记录
     * 
     * @param id 记录ID
     * @return 操作结果JSON字符串
     */
    String delete(String id);

    /**
     * 编辑记录内容
     * 
     * @param id          记录ID
     * @param editContent 编辑内容
     * @return 操作结果JSON字符串
     */
    String edit(String id, Object editContent);

    /**
     * 设置是否固定
     * 
     * @param id       记录ID
     * @param isPinned 是否固定
     * @return 操作结果JSON字符串
     */
    String setIsPinned(String id, boolean isPinned);

    /**
     * 通过记录查询条件获取记录列表
     * 
     * @param condition 记录查询条件JSON字符串
     * @return 记录列表
     */
    String getRecordsByCondition(String conditionJSON);

    /**
     * 通过记录查询条件获取记录数
     * 
     * @param conditionJSON 记录查询条件JSON字符串
     * @return 记录数
     */
    int getRecordsCountByCondition(String conditionJSON);
}
