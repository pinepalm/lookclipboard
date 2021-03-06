/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-30 11:18:09
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-10 00:28:18
 * 
 * @Description: 扩展工具类
 */
package com.buaa.lookclipboard.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Supplier;
import com.buaa.commons.foundation.ServiceLoaderEx;
import com.buaa.lookclipboard.service.impl.LogService;
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
     * @param configs     配置文件
     * @return 扩展服务加载器
     */
    public static <T> ServiceLoaderEx<T> createServiceLoader(Iterable<String> folderPaths, Class<T> service, Supplier<Enumeration<URL>> configs) {
        if (folderPaths == null)
            return null;

        List<URL> urls = new ArrayList<>();

        for (String folderPath : folderPaths) {
            if (folderPath != null) {
                try {
                    File folder = new File(folderPath);
                    Collection<File> files = FileUtils.listFiles(
                            folder,
                            FileFilterUtils.suffixFileFilter("jar"),
                            DirectoryFileFilter.INSTANCE);

                    for (File file : files) {
                        urls.add(file.toURI().toURL());
                    }
                } catch (Exception e) {
                    LogService.getInstance().error("extensions load failed", e);
                }
            }
        }

        ClassLoader classLoader = URLClassLoader.newInstance(
                urls.toArray(new URL[] {}),
                Thread.currentThread().getContextClassLoader());

        return ServiceLoaderEx.load(service, classLoader, configs);
    }

    private ExtensionUtil() {

    }
}
