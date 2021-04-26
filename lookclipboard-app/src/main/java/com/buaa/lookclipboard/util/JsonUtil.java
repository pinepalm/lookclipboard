/*
 * @Author: Zhe Chen
 * @Date: 2021-04-26 22:25:57
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 22:28:43
 * @Description: file content
 */
package com.buaa.lookclipboard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public final class JsonUtil {
    public static String toJSONString(Object value) {
        try {
            ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JsonUtil() {

    }
}