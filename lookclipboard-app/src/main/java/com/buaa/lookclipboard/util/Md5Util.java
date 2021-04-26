/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 20:43:33
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-22 20:45:53
 * @Description: file content
 */
package com.buaa.lookclipboard.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public final class Md5Util {
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    private Md5Util() {

    }
}
