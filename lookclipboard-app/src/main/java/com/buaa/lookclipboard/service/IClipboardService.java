/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-03 22:30:56
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-10 19:34:59
 * 
 * @Description: 剪贴板服务接口
 */
package com.buaa.lookclipboard.service;

/**
 * 剪贴板服务接口
 */
public interface IClipboardService extends IAppService {
    /**
     * 获取是否监控剪贴板
     * 
     * @return 是否监控
     */
    boolean isMonitored();

    /**
     * 设置是否监控剪贴板
     * 
     * @param monitored 是否监控
     */
    void setMonitored(boolean monitored);

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
     * 删除批量记录
     * 
     * @param idsJSON 记录ID列表JSON字符串
     * @return 操作结果JSON字符串
     */
    String deleteBatch(String idsJSON);

    /**
     * 删除全部记录
     * 
     * @return 操作结果JSON字符串
     */
    String deleteAll();

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
     * @param conditionJSON 记录查询条件JSON字符串
     * @return 记录列表
     */
    String getRecordsByCondition(String conditionJSON);

    /**
     * 通过记录查询条件获取记录数
     * 
     * @param conditionJSON 记录查询条件JSON字符串
     * @return 记录数
     */
    String getRecordsCountByCondition(String conditionJSON);
}
