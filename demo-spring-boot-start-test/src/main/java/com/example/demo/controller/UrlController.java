package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.UrlEntity;
import com.example.demo.mapper.UrlMapper;
import com.example.demo.util.JsoupUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.ArrayList;
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

    /**
     * Jsoup 模拟浏览器发起请求
     */
    private static final String URL = "https://search.jd.com/Search?keyword=mysql&page=";

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

    @PostMapping("books")
    public  void book() throws Exception {
        Map<String, List<Book>> map = new HashMap<>(16);

        for (int i = 1; i < 10; i++) {
            List<Book> bookList = new ArrayList<>();
            //url 最长解析时间
            Document document = Jsoup.parse(new URL(URL.concat(String.valueOf(i))), 30000);
            System.out.println(document.html());
            Element element = document.getElementById("J_goodsList");
            // 获取J_goodsList下的所有的li元素
            Elements elements = element.getElementsByTag("li");
            for (Element ele : elements) {
                String img = ele.getElementsByTag("img").eq(0).attr("data-lazy-img");
                String text = ele.getElementsByClass("p-name").eq(0).text();
                String price = ele.getElementsByClass("p-price").eq(0).text();
                String shopName = ele.getElementsByClass("p-shopnum").eq(0).text();
                bookList.add(new Book(img, text, price, shopName));
            }
            map.put(String.valueOf(i), bookList);
        }
        map.forEach((s, bookList) -> urlMapper.insertBook(bookList));
    }

}
