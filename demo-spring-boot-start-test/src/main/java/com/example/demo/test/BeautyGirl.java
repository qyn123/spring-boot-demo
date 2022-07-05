package com.example.demo.test;

import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author qiaoyanan
 * date:2022/07/04 14:46
 */
public class BeautyGirl {
    public static void main(String[] args) throws IOException {
        //Jsoup 模拟浏览器发起请求
        String website = "http://www.netbian.com/meinv/";
        for (int m = 2; m < 20; m++) {
            Connection.Response response = Jsoup
                    .connect("http://www.netbian.com/meinv/index_"+ m +".htm" )
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")
                    .ignoreContentType(true)
                    .timeout(100000)
                    .execute();
            //响应头
            System.out.println(response.header("Content-Type"));
            //响应体
            System.out.println(response.body());
            String html = response.body();
            //Jsoup 解析HTML
            Document dom = Jsoup.parse(html);
            //选择器
            //获取多个
            //#pic-detail > div > div.col-sm-9 > div.page-content > a:nth-child(2) > img
            Elements select = dom.select("#main > div.list > ul > li > a > img");
            //获取单个
            for (Element element : select) {
                String attr = element.attr("src");
                int i = attr.lastIndexOf("/");
                String filename = attr.substring(i + 1);
                System.out.println(filename);
                System.out.println(attr);
                Connection.Response imgResponse = Jsoup.connect(attr)
                        .ignoreContentType(true)
                        .timeout(100000)
                        //100M的缓冲区
                        .maxBodySize(100 * 1024 * 1024)
                        .execute();
                //因为图片是二进制  音频  视频  图片 都用
                byte[] bytes = imgResponse.bodyAsBytes();
                IOUtils.write(bytes, new FileOutputStream(new File("E://qyn//" + filename)));
            }
        }
    }
}
