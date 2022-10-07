package com.qiaoyn.upload.controller;

import com.qiaoyn.upload.constant.SimpleMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qiaoyanan
 * date:2022/08/15 10:12
 */
@RestController
@Slf4j
public class ArticleController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        //获取文件名
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        fileName = getFileName(fileName);
        String filepath = getUploadPath();
        if (!file.isEmpty()) {
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filepath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                rabbitTemplate.convertAndSend(SimpleMqConstant.DIRECT_EXCHANGE_NAME, SimpleMqConstant.ROUTING_KEY, filepath);
            } catch (FileNotFoundException e) {
                log.info("上传文件失败 FileNotFoundException:{}",e.getMessage());
            } catch (IOException e) {
                log.info("上传文件失败 IOException:{}",e.getMessage());
            }
        } else {
            log.info("上传文件失败，文件为空");
        }
    }

    /**
     * 文件名后缀前添加一个时间戳
     */
    private String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        //设置时间格式
        final SimpleDateFormat sDateFormate = new SimpleDateFormat("yyyymmddHHmmss");
        // 当前时间
        String nowTimeStr = sDateFormate.format(new Date());
        fileName = fileName.substring(0, index) + "_" + nowTimeStr + fileName.substring(index);
        return fileName;
    }

    /**
     * 获取当前系统路径
     */
    private String getUploadPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert path != null;
        if (!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(), "static/upload/");
        if (!upload.exists()) {
            upload.mkdirs();
        }
        return upload.getAbsolutePath();
    }
}
