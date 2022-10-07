package com.qiaoyn.upload.config;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author qiaoyanan
 * date:2022/08/19 20:48
 */
public class Test {
    public static void main(String[] args) {
        String path = "E:\\qiao.jpg";
        File file = new File(path);
        handleDpi(file, 390, 567);
    }

    /**
     * 改变图片DPI
     *
     * @param file
     * @param xDensity
     * @param yDensity
     */
    public static void handleDpi(File file, int xDensity, int yDensity) {
        try {
            BufferedImage image = ImageIO.read(file);
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(new FileOutputStream(file));
            JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image);
            jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
            jpegEncoder.setJPEGEncodeParam(jpegEncodeParam);
            jpegEncodeParam.setQuality(0.75f, false);
            jpegEncodeParam.setXDensity(xDensity);
            jpegEncodeParam.setYDensity(yDensity);
            jpegEncoder.encode(image, jpegEncodeParam);
            image.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
