package com.example.demo.test;

import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
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
public class TestJsoup {
    public static void main(String[] args) throws IOException {
        //Jsoup 模拟浏览器发起请求
        String website = "https://dou.yuanmazg.com/";

        for (int m = 1; m < 20; m++) {
            Connection.Response response = Jsoup
                    .connect("https://dou.yuanmazg.com/?page=" + m)
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
            Elements select = dom.select("#home > div > div.col-sm-9 > div.nav-slide > div > ul > a > div.pic-content.text-center > div > img");
            //获取单个
            for (Element element : select) {
                String realurl = website + element.attr("data-original");
                int i = element.attr("data-original").lastIndexOf("/");
                String filename = element.attr("data-original").substring(i + 1);
                System.out.println(filename);
                System.out.println(realurl);
                Connection.Response imgResponse = Jsoup.connect(realurl)
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
