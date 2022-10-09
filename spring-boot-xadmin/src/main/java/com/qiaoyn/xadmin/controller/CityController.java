package com.qiaoyn.xadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiaoyanan
 * date:2022/10/08 16:54
 */
@Controller
public class CityController {

    @GetMapping("/city")
    public String city() {
        return "city";
    }

    @GetMapping("/cate")
    public String cate() {
        return "cate";
    }

    @GetMapping("/role-add")
    public String roleAdd() {
        return "role-add";
    }

    @GetMapping("/unicode")
    public String unicode() {
        return "unicode";
    }
}
