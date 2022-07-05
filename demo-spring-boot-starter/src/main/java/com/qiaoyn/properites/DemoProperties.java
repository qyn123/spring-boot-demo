package com.qiaoyn.properites;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qiaoyanan
 * date:2022/06/28 15:45
 */
@ConfigurationProperties(prefix = "demo")
public class DemoProperties {
    private String sayWhat;
    private String toWho;

    public String getSayWhat() {
        return sayWhat;
    }

    public void setSayWhat(String sayWhat) {
        this.sayWhat = sayWhat;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }
}
