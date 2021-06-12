/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-21 21:11:11
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-12 22:46:51
 * 
 * @Description: 剪贴板图像扩展
 */
package com.buaa.lookclipboard.service.core.impl;

import java.io.File;
import java.util.Objects;
import com.buaa.commons.foundation.Ref;
import com.buaa.commons.util.StringUtil;
import com.buaa.commons.util.javafx.ImageUtil;
import com.buaa.lookclipboard.AppConfig;
import com.buaa.lookclipboard.model.IRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * 剪贴板图像扩展
 */
public final class ClipboardImageExtension extends ClipboardExtension<Image> {
    private final static String IMAGE_DATA = "img";

    private File getAppImageDataFolder() {
        File appDataFolder = AppConfig.getInstance().getAppDataFolder();
        File imageDataFolder = FileUtils.getFile(appDataFolder, IMAGE_DATA);
        if (!imageDataFolder.exists()) {
            imageDataFolder.mkdirs();
        }
        return imageDataFolder;
    }

    private File getAppImageDataFile(String name) {
        return FileUtils.getFile(getAppImageDataFolder(), name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needReceiveInternal(IRecord lastRecord, Image content) {
        if (lastRecord.getContent() == null && content == null) {
            return false;
        }

        try {
            File imageFile = getAppImageDataFile(lastRecord.getContent());
            Image lastImage = ImageUtil.read(imageFile);
            return !ImageUtil.deepEquals(lastImage, content);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReceivedInternal(IRecord newRecord, Image content, Ref<String> outContent) throws Exception {
        String imageFileName = StringUtil.interpolate(
                "${id}.${formatName}", new Object[][] 
                {
                    {"id", newRecord.getID()}, 
                    {"formatName", ImageUtil.PNG}
                });
        File imageFile = getAppImageDataFile(imageFileName);

        ImageUtil.write(content, imageFile, ImageUtil.PNG);
        outContent.set(imageFileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFormat getDataFormat() {
        return DataFormat.IMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCopied(IRecord record, Ref<ClipboardContent> outContent) throws Exception {
        File imageFile = getAppImageDataFile(record.getContent());
        Image image = ImageUtil.read(imageFile);

        ClipboardContent content = new ClipboardContent();
        content.putImage(image);
        outContent.set(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEdited(IRecord record, Object editContent, Ref<String> outContent) throws Exception {
        Image image = ImageUtil.fromBase64(Objects.toString(editContent, StringUtils.EMPTY));
        File imageFile = getAppImageDataFile(record.getContent());

        ImageUtil.write(image, imageFile, ImageUtil.PNG);

        // 内容保持不变
        outContent.set(record.getContent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleted(IRecord record) throws Exception {
        File imageFile = getAppImageDataFile(record.getContent());
        FileUtils.forceDelete(imageFile);
    }
}
