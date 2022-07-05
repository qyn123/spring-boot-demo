package com.example.demo.controller;

import com.qiaoyn.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiaoyanan
 * date:2022/06/28 16:04
 */
@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;

    @GetMapping("/say")
    public String sayWhat(){
        return demoService.say();
    }

}
