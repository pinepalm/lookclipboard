/*
 * @Author: Zhe Chen
 * @Date: 2021-04-21 20:35:05
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 22:37:40
 * @Description: file content
 */
package com.buaa.lookclipboard.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.BiFunction;

import com.buaa.appmodel.core.datatransfer.Clipboard;
import com.buaa.lookclipboard.App;
import com.buaa.lookclipboard.dao.RecordDao;
import com.buaa.lookclipboard.domain.Record;
import com.buaa.lookclipboard.model.ActionResult;
import com.buaa.lookclipboard.model.IActionResult;

import javafx.scene.input.DataFormat;

import com.buaa.lookclipboard.service.core.IClipboardExtension;
import com.buaa.lookclipboard.util.JsonUtil;

public final class ClipboardService {
    public final static ClipboardService instance = new ClipboardService();

    private boolean isCopying;

    private final Map<String, DataFormat> dataFormats;
    private final Map<String, DataFormat> readonlyDataFormats;
    private final List<IClipboardExtension> extensions;

    private ClipboardService() {
        dataFormats = new HashMap<>();
        readonlyDataFormats = Collections.unmodifiableMap(dataFormats);

        extensions = new ArrayList<>();
        File extensionsFolder = new File(getClass().getResource("/extensions").getPath());
        File[] extensionFiles = extensionsFolder.listFiles((dir, name) -> name.endsWith(".jar"));
        List<URL> extensionUrls = new ArrayList<>();
        for (File extensionFile : extensionFiles) {
            try {
                if (extensionFile.isFile()) {
                    extensionUrls.add(extensionFile.toURI().toURL());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        ClassLoader extensionsClassLoader = URLClassLoader.newInstance(extensionUrls.toArray(new URL[] {}),
                Thread.currentThread().getContextClassLoader());
        ServiceLoader<IClipboardExtension> serviceLoader = ServiceLoader.load(IClipboardExtension.class,
                extensionsClassLoader);
        for (IClipboardExtension extension : serviceLoader) {
            extensions.add(extension);
        }

        Clipboard.contentChanged.addEventHandler((sender, args) -> {
            if (isCopying) {
                isCopying = false;
                return;
            }

            for (IClipboardExtension extension : extensions) {
                Object content = Clipboard.getContent(extension.getDataFormat());
                if (content != null) {
                    Record record = Record.createNow(extension.getDataFormat());
                    IActionResult result = extension.onReceived(record, content);
                    if (result != null && result.getStatusCode() == 200) {
                        record.setInfo(result.getInfo());

                        String recordJson = JsonUtil.toJSONString(record);
                        if (recordJson != null) {
                            App.getWebEngine().executeScript(String.format("addRecord('%s')",
                                    recordJson.replace("\\", "\\\\").replace("'", "\\'")));
                        }
                    } else {
                        System.out.println(result.getInfo());
                    }

                    break;
                }
            }
        });
    }

    private String handle(String id, BiFunction<Record, IClipboardExtension, String> function) {
        Record record = RecordDao.instance.get(id);
        if (record != null) {
            for (IClipboardExtension extension : extensions) {
                if (record.getDataFormat().equals(extension.getDataFormat())) {
                    return function.apply(record, extension);
                }
            }
        }

        return null;
    }

    public String copy(String id) {
        return handle(id, (record, extension) -> {
            isCopying = true;
            IActionResult result = extension.onCopied(record);
            if (result == null || result.getStatusCode() != 200) {
                isCopying = false;
            }
            
            return JsonUtil.toJSONString(result);
        });
    }

    public String delete(String id) {
        return handle(id, (record, extension) -> {
            IActionResult result = extension.onDeleted(record);
            if (result != null && result.getStatusCode() == 200) {
                RecordDao.instance.delete(record);
            }
            
            return JsonUtil.toJSONString(result);
        });
    }

    public String edit(String id, Object editInfo) {
        return handle(id, (record, extension) -> {
            IActionResult result = extension.onEdited(record, editInfo);
            if (result != null && result.getStatusCode() == 200) {
                RecordDao.instance.update(record);
            }
            
            return JsonUtil.toJSONString(result);
        });
    }

    // 这个方法不知道要不要提供接口实现(onPinSwitched), 目前仅预览
    public String switchPin(String id) {
        Record record = RecordDao.instance.get(id);
        if (record != null) {
            record.setIsPinned(!record.getIsPinned());
            RecordDao.instance.update(record);
        }

        return JsonUtil.toJSONString(new ActionResult(null, 200));
    }

    public Map<String, DataFormat> getDataFormats() {
        return readonlyDataFormats;
    }
}