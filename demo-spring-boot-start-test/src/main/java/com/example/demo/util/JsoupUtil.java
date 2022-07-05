package com.example.demo.util;

import com.example.demo.entity.UrlEntity;
import com.sun.xml.internal.ws.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/07/04 15:25
 */
public class JsoupUtil {

    /**
     * 发起请求
     */
    public static Connection.Response launchResponse(String url, int m) {
        Connection.Response response = null;
        try {
            response = Jsoup
                    .connect(url + m)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")
                    .ignoreContentType(true)
                    .timeout(100000)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发起请求
     */
    public static Connection.Response launchResponse(String url) {
        Connection.Response response = null;
        try {
            response = Jsoup
                    .connect(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")
                    .ignoreContentType(true)
                    .timeout(100000)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    //    public static void parseHtml(String html, String website,String pathName,String cssQuery,String attributeKey) {
//        //Jsoup 解析HTML
//        Document dom = Jsoup.parse(html);
//        //选择器
//        //获取多个
//        //#pic-detail > div > div.col-sm-9 > div.page-content > a:nth-child(2) > img
//        Elements select = dom.select(cssQuery);
//        //获取单个
//        for (Element element : select) {
//            int i = element.attr(attributeKey).lastIndexOf("/");
//            String filename = element.attr(attributeKey).substring(i + 1);
//            System.out.println(filename);
//            System.out.println(website + element.attr(attributeKey));
//            Connection.Response imgResponse;
//            try {
//                imgResponse = returnResponse(website + element.attr(attributeKey));
//                //因为图片是二进制  音频  视频  图片 都用
//                byte[] bytes = imgResponse.bodyAsBytes();
//                IOUtils.write(bytes, new FileOutputStream(new File(pathName + filename)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public static List<UrlEntity> parseHtml(String html, String website, String cssQuery, String attributeKey) {
        List<UrlEntity> list = new ArrayList<>();
        //Jsoup 解析HTML
        Document dom = Jsoup.parse(html);
        //选择器
        //获取多个
        //#pic-detail > div > div.col-sm-9 > div.page-content > a:nth-child(2) > img
        Elements select = dom.select(cssQuery);
        //获取单个
        for (Element element : select) {
            int i = element.attr(attributeKey).lastIndexOf("/");
            String filename = element.attr(attributeKey).substring(i + 1);
            System.out.println(filename);
            System.out.println(website + element.attr(attributeKey));
            list.add(new UrlEntity(website + element.attr(attributeKey)));
        }
        return list;
    }


    public static List<UrlEntity> parseHtml(String html, String cssQuery, String attributeKey) {
        List<UrlEntity> list = new ArrayList<>();
        //Jsoup 解析HTML
        Document dom = Jsoup.parse(html);
        //选择器
        //获取多个
        //#pic-detail > div > div.col-sm-9 > div.page-content > a:nth-child(2) > img
        Elements select = dom.select(cssQuery);
        //获取单个
        for (Element element : select) {
            String url = element.attr(attributeKey);
            if (!StringUtil.isBlank(url)) {
                if (url.startsWith("//")) {
                    list.add(new UrlEntity(url.substring(2)));
                } else {
                    list.add(new UrlEntity(url));
                }
            }
        }
        return list;
    }

    public static Connection.Response returnResponse(String realUrl) {
        try {
            return Jsoup.connect(realUrl)
                    .ignoreContentType(true)
                    .timeout(100000)
                    //100M的缓冲区
                    .maxBodySize(100 * 1024 * 1024)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
