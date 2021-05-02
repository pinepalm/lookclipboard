/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 19:31:45
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 13:48:13
 * @Description: 剪贴板
 */
package com.buaa.appmodel.core.datatransfer;

import com.buaa.commons.event.Event;
import com.buaa.commons.event.EventArgs;
import com.sun.glass.ui.ClipboardAssistance;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板
 */
public final class Clipboard {
    private final static javafx.scene.input.Clipboard systemClipboard = javafx.scene.input.Clipboard
            .getSystemClipboard();

    private final static ClipboardAssistance monitor = new ClipboardAssistance(com.sun.glass.ui.Clipboard.SYSTEM) {
        @Override
        public void contentChanged() {
            contentChanged.invoke(null, null);
        }
    };

    /**
     * 内容更改事件
     */
    public final static Event<EventArgs> contentChanged = new Event<>();

    /**
     * 判断是否存在指定数据格式的内容
     * 
     * @param dataFormat 数据格式
     * @return 是否存在指定数据格式的内容
     */
    public static boolean hasContent(DataFormat dataFormat) {
        return systemClipboard.hasContent(dataFormat);
    }

    /**
     * 获取指定数据格式的内容
     * 
     * @param dataFormat 数据格式
     * @return 指定数据格式的内容，若不存在，则返回null
     */
    public static Object getContent(DataFormat dataFormat) {
        return systemClipboard.getContent(dataFormat);
    }

    /**
     * 设置内容
     * 
     * @param content 内容
     * @return 是否设置成功
     */
    public static boolean setContent(ClipboardContent content) {
        return systemClipboard.setContent(content);
    }

    /**
     * 获取全部MIME类型
     * 
     * @return 全部MIME类型
     */
    public static String[] getMimeTypes() {
        return monitor.getMimeTypes();
    }

    private Clipboard() {

    }
}
