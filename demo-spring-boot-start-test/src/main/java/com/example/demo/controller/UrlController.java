package com.example.demo.controller;

import com.example.demo.entity.UrlEntity;
import com.example.demo.mapper.UrlMapper;
import com.example.demo.util.JsoupUtil;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author qiaoyanan
 * date:2022/07/04 17:03
 */
@RestController
public class UrlController {

    @Autowired
    private UrlMapper urlMapper;

    @PostMapping("test")
    public void test() {
        int M = 10;
        Map<Integer, List<UrlEntity>> map = new HashMap<>(16);
        List<UrlEntity> list;
        for (int i = 1; i <= M; i++) {
            Connection.Response response = JsoupUtil.launchResponse("https://dou.yuanmazg.com/?page=", i);
            list = JsoupUtil.parseHtml(response.body(), "https://dou.yuanmazg.com/",
                    "#home > div > div.col-sm-9 > div.nav-slide > div > ul > a > div.pic-content.text-center > div > img",
                    "data-original");
            map.put(i, list);
        }

        map.forEach((integer, listUrl) -> urlMapper.insert(listUrl));

    }


    @PostMapping("hello")
    public void testBeauty() {
        int M = 10;
        Map<Integer, List<UrlEntity>> map = new HashMap<>(16);
        List<UrlEntity> list;
        for (int i = 2; i <= M; i++) {
            Connection.Response response = JsoupUtil.launchResponse("https://www.woyaogexing.com/shouji/z/nvsheng/index_" + i + ".html");
            list = JsoupUtil.parseHtml(response.body(),
                    "#main > div.list-main.mt10.cl > div.list-left-sj.sjList.z > div.pMain > div > a.img > img",
                    "src");
            map.put(i, list);
        }

        map.forEach((integer, listUrl) -> urlMapper.insert(listUrl));

    }


    @PostMapping("book")
    public void testBook() {
        int M = 5;
        Map<Integer, List<UrlEntity>> map = new HashMap<>(16);
        List<UrlEntity> list;
        for (int i = 2; i <= M; i++) {
            Connection.Response response = JsoupUtil.launchResponse("https://search.jd.com/Search?keyword=java&page=" + i);
            list = JsoupUtil.parseHtml(response.body(),
                    "#J_goodsList > ul > li> div > div.p-img > a > img",
                    "data-lazy-img");
            map.put(i, list);
        }

        map.forEach((integer, listUrl) -> urlMapper.insert(listUrl));

    }

}
