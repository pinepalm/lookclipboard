/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-12 16:04:02
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:58:52
 * 
 * @Description: 操作结果try-catch-finally上下文
 */
package com.buaa.lookclipboard.model;

import com.buaa.commons.lang.ITryContext;

/**
 * 操作结果try-catch-finally上下文
 */
public class ActionResultTryContext implements ITryContext {
    private IActionResult result;

    /**
     * 获取操作结果
     * 
     * @return 操作结果
     */
    public IActionResult getResult() {
        return result;
    }

    /**
     * 设置操作结果
     * 
     * @param result 操作结果
     */
    public void setResult(IActionResult result) {
        this.result = result;
    }
}
