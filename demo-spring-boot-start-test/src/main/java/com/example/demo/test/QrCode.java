/*
package com.example.demo.test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author qiaoyanan
 * date:2022/07/27 9:35
 *//*

public class QrCode {
    public static void main(String[] args) {
        getQrCode("你好", 300, 300, "E://", "QRCode10", "jpg");

    }

    */
/**
     * @param contents 要生成二维码内容
     * @param width 宽度
     * @param height 高度
     * @param fileName 文件名
     * @param filePath 文件存放路径
     * @param format 文件格式(png,jpg)
     *//*

    public static void getQrCode(String contents, Integer width, Integer height, String filePath, String fileName, String format) {
        //创建一个map集合，存放字符集
        Map<EncodeHintType, Object> hints = new HashMap<>(1);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //定义一个矩阵对象,生成二维码的关键代码：实例化一个矩阵对象
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            //创建一个路径对象
            Path path = FileSystems.getDefault().getPath(filePath + fileName + "." + format);
            //将矩阵对象转化为图片
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);
            System.err.println("二维码生成完毕");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
*/
