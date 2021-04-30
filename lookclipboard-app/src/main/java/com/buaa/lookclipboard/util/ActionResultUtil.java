/*
 * @Author: Zhe Chen
 * @Date: 2021-04-30 19:37:29
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 19:39:05
 * @Description: 操作结果工具类
 */
package com.buaa.lookclipboard.util;

import com.buaa.lookclipboard.model.IActionResult;

/**
 * 操作结果工具类
 */
public final class ActionResultUtil {
    /**
     * 操作是否成功
     * 
     * @param result 操作结果
     * @return 是否成功
     */
    public static boolean isSuccess(IActionResult result) {
        return result != null && result.getStatusCode() == 200;
    }

    private ActionResultUtil() {

    }
}