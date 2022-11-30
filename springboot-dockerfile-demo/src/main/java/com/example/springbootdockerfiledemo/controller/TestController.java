package com.example.springbootdockerfiledemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiaoyanan
 * date:2022/11/17 10:41
 */
@RestController
public class TestController {


    @GetMapping("/hello")
    public String hello() {
        return "hello,docker ~";
    }

}

