/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 20:43:33
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 15:21:50
 * @Description: MD5工具类
 */
package com.buaa.lookclipboard.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * MD5工具类
 */
public final class Md5Util {
    /**
     * MD5加密
     * 
     * @param content 内容
     * @return 加密字符串
     */
    public static String md5(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    private Md5Util() {

    }
}
