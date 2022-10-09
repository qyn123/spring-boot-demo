package com.qiaoyn.xadmin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qiaoyanan
 * date:2022/10/08 15:58
 */
@Controller
@Slf4j
public class LoginController {


    @GetMapping({"/","/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
