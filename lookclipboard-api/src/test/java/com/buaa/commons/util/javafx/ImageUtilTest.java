/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-14 17:49:13
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 14:47:37
 * 
 * @Description: 图像工具类测试
 */
package com.buaa.commons.util.javafx;

import static org.junit.Assert.assertTrue;
import java.io.File;
import org.junit.Test;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

/**
 * 图像工具类测试
 */
public class ImageUtilTest {
    private void init() {
        new JFXPanel();
    }

    /**
     * 测试图像Base64
     */
    @Test
    public void testImageBase64() {
        init();

        try {
            Image image = ImageUtil.read(new File("D:\\软件\\编程\\资源\\face_sample.jpg"));
            String base64 = ImageUtil.toBase64(image, ImageUtil.PNG);
            Image image1 = ImageUtil.fromBase64(base64);

            assertTrue(ImageUtil.deepEquals(image, image1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
