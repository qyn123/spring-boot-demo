package com.example.springbootdockerfiledemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author qiaoyanan
 * date:2022/11/17 10:41
 */
@RestController
public class TestController {


    @GetMapping("/hello")
    public String hello() {
        try {
            InetAddress host = Inet4Address.getLocalHost();
            return "hello," + host.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "hello,docker~~";
    }

}

