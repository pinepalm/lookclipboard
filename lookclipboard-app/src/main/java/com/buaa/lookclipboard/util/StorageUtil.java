/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 21:56:45
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-22 21:58:34
 * @Description: file content
 */
package com.buaa.lookclipboard.util;

import java.io.File;

public final class StorageUtil {
    public static File getLocalAppDataFolder() {
        return new File(System.getenv("LOCALAPPDATA"));
    }

    private StorageUtil() {

    }
}
