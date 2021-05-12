/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-30 11:18:09
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-07 00:46:53
 * 
 * @Description: 扩展工具类
 */
package com.buaa.lookclipboard.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

/**
 * 扩展工具类
 */
public final class ExtensionUtil {
    /**
     * 创建扩展服务加载器
     * 
     * @param <T>         扩展服务类型
     * @param folderPaths 扩展文件夹路径
     * @param service     扩展服务类对象
     * @return 扩展服务加载器
     */
    public static <T> ServiceLoader<T> createServiceLoader(Iterable<String> folderPaths,
            Class<T> service) {
        if (folderPaths == null)
            return null;

        List<URL> urls = new ArrayList<>();

        for (String folderPath : folderPaths) {
            if (folderPath != null) {
                try {
                    File folder = new File(folderPath);
                    Collection<File> files = FileUtils.listFiles(folder,
                            FileFilterUtils.suffixFileFilter("jar"), DirectoryFileFilter.INSTANCE);
                    for (File file : files) {
                        urls.add(file.toURI().toURL());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        ClassLoader classLoader = URLClassLoader.newInstance(urls.toArray(new URL[] {}),
                Thread.currentThread().getContextClassLoader());
        return ServiceLoader.load(service, classLoader);
    }

    private ExtensionUtil() {

    }
}
