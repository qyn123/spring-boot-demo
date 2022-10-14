package com.qiaoyn.xadmin.controller;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author qiaoyanan
 * date:2022/10/14 10:36
 */
@Controller
public class SwaggerController {

    @Resource
    Environment environment;

    @GetMapping("/swagger")
    public String swagger() {
        String hostAddress = "127.0.0.1";
        try {
             hostAddress = Inet4Address.getLocalHost().getHostAddress();
            System.out.println(hostAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = environment.getProperty("server.port");
        StringBuilder s  = new StringBuilder();
        s.append("redirect:http://").append(hostAddress).append(":").append(port).append("/swagger-ui.html#/");
        return s.toString();
    }
}
