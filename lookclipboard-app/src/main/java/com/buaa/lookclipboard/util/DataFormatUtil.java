/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-02 15:07:48
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-06 23:17:36
 * 
 * @Description: 数据类型工具类
 */
package com.buaa.lookclipboard.util;

import org.apache.commons.lang3.ArrayUtils;

import javafx.scene.input.DataFormat;

/**
 * 数据类型工具类
 */
public final class DataFormatUtil {
    /**
     * 将数据类型转为JSON字符串
     * 
     * @param dataFormat 数据类型
     * @return JSON字符串
     */
    public static String toJSON(DataFormat dataFormat) {
        if (dataFormat == null) {
            return null;
        }

        return JsonUtil.stringify(dataFormat.getIdentifiers());

    }

    /**
     * 将JSON字符串转回数据类型
     * 
     * @param json JSON字符串
     * @return 数据类型
     */
    public static DataFormat fromJSON(String json) {
        String[] identifiers = JsonUtil.parse(json, String[].class);
        if (ArrayUtils.isEmpty(identifiers)) {
            return null;
        }

        DataFormat dataFormat = DataFormat.lookupMimeType(identifiers[0]);
        if (dataFormat == null) {
            try {
                dataFormat = new DataFormat(identifiers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dataFormat;
    }

    private DataFormatUtil() {

    }
}
