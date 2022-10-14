package com.qiaoyn.xadmin.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author qiaoyanan
 * date:2022/10/14 11:36
 */
@Configuration
public class MyLocalResolverConfig implements LocaleResolver {
    // 完成自定义 区域解析方式
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 获取页面手动传递的语言参数值 : zh_CN   en_US   ""
        String l = request.getParameter("l");
        Locale locale;
        if (!StringUtils.isEmpty(l)) {
            // 如果参数不为空，就根据参数值，进行手动语言切换
            String[] s = l.split("_");
            locale = new Locale(s[0], s[1]);
        } else {
            // Accept-Language: zh-CN,zh;q=0.9
            String header = request.getHeader("Accept-Language");
            String[] split = header.split(",");
            String[] split1 = split[0].split("-");
            locale = new Locale(split1[0], split1[1]);
        }
        return locale;
    }
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocalResolverConfig();
    }
}
