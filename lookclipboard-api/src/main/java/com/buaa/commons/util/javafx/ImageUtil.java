/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-05-14 13:39:49
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-06-03 20:40:57
 * 
 * @Description: 图像工具类
 */
package com.buaa.commons.util.javafx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.Objects;
import javax.imageio.ImageIO;
import com.buaa.commons.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritablePixelFormat;

/**
 * 图像工具类
 */
public final class ImageUtil {
    /**
     * bmp格式
     */
    public final static String BMP = "bmp";
    /**
     * png格式
     */
    public final static String PNG = "png";
    /**
     * jpg格式
     */
    public final static String JPG = "jpg";
    /**
     * gif格式
     */
    public final static String GIF = "gif";

    /**
     * 将图像转为像素字节数组
     * 
     * @param img 图像
     * @return 像素字节数组
     */
    public static byte[] toPixels(Image img) {
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        byte[] buffer = new byte[width * height * 4];
        WritablePixelFormat<ByteBuffer> pixelformat = PixelFormat.getByteBgraInstance();

        img.getPixelReader().getPixels(0, 0, width, height, pixelformat, buffer, 0, width * 4);

        return buffer;
    }

    /**
     * 将图像转为字节数组
     * 
     * @param img        图像
     * @param formatName 格式名称
     * @return 字节数组
     * @throws IOException 当数据写入时可能有IO异常
     */
    public static byte[] toBytes(Image img, String formatName) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, formatName, stream);
        return stream.toByteArray();
    }

    /**
     * 图像是否深度相等
     * 
     * @param img1 图像1
     * @param img2 图像2
     * @return 是否深度相等
     */
    public static boolean deepEquals(Image img1, Image img2) {
        if (img1 == null && img2 == null) {
            return true;
        }
        if (img1 == null || img2 == null) {
            return false;
        }

        double width1 = img1.getWidth();
        double width2 = img2.getWidth();
        double height1 = img1.getHeight();
        double height2 = img2.getHeight();

        if (width1 != width2 || height1 != height2) {
            return false;
        }

        byte[] buffer1 = toPixels(img1);
        byte[] buffer2 = toPixels(img2);

        return Objects.equals(DigestUtils.md5Hex(buffer1), DigestUtils.md5Hex(buffer2));
    }

    /**
     * 从文件读取图像
     * 
     * @param file 文件
     * @return 图像
     * @throws MalformedURLException 当文件转为URL时可能有URL异常
     */
    public static Image read(File file) throws MalformedURLException {
        return new Image(file.toURI().toURL().toExternalForm());
    }

    /**
     * 将图像写入文件
     * 
     * @param img        图像
     * @param file       文件
     * @param formatName 格式名称
     * @throws IOException 当数据写入时可能有IO异常
     */
    public static void write(Image img, File file, String formatName) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        ImageIO.write(bufferedImage, formatName, file);
    }

    /**
     * 将Base64字符串转为图像
     * 
     * @param base64 Base64字符串
     * @return 图像
     * @throws IOException 当数据读取时可能有IO异常
     */
    public static Image fromBase64(String base64) throws IOException {
        String noHeaderBase64 = base64.replaceFirst("data[:]image[/]([a-z])+;base64,", "").trim();
        byte[] buffer = Base64.decodeBase64(noHeaderBase64);
        ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
        BufferedImage bufferedImage = ImageIO.read(stream);

        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * 将图像转为Base64字符串
     * 
     * @param img        图像
     * @param formatName 格式名称
     * @return Base64字符串
     * @throws IOException 当数据写入时可能有IO异常
     */
    public static String toBase64(Image img, String formatName) throws IOException {
        byte[] buffer = toBytes(img, formatName);
        
        return StringUtil.interpolate(
                "data:image/${formatName};base64,${base64}", new Object[][] 
                {
                    {"formatName", formatName}, 
                    {"base64", Base64.encodeBase64String(buffer)
                }});
    }

    private ImageUtil() {

    }
}
