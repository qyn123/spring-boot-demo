package com.qiaoyn.config;

import com.qiaoyn.properites.DemoProperties;
import com.qiaoyn.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(DemoProperties.class)
@ConditionalOnProperty(prefix = "demo",name = "isopen")
public class DemoConfig {
    @Autowired
    private DemoProperties demoProperties;

    @Bean(name = "demo")
    public DemoService demoService() {
        return new DemoService(demoProperties.getSayWhat(), demoProperties.getToWho());
    }
}