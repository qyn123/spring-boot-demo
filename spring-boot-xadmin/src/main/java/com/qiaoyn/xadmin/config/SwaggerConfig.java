package com.qiaoyn.xadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author qiaoyanan
 * date:2022/10/14 10:06
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket Docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("swaggerDemo").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.qiaoyn.xadmin.controller")).paths(PathSelectors.any()).build();
    }
    // 预览地址:swagger-ui.html
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("qiaoyn", "https://www.baidu.com/", "1750718285@qq.com");

        return new ApiInfo("X-Admin系统在线接口文档",
                "努力从来不是为了证明什么,只是为了超越自己",
                "v1.0", "https://www.baidu.com/",
                contact, "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
