package com.qiaoyn.xadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qiaoyanan
 * date:2022/10/08 15:57
 */
@SpringBootApplication
@MapperScan("com.qiaoyn.xadmin.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
