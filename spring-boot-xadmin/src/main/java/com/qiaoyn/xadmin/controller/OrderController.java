package com.qiaoyn.xadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiaoyanan
 * date:2022/10/08 16:52
 */
@Controller
public class OrderController {

    @GetMapping("/order-add")
    public String orderAdd() {
        return "/order/order-add";
    }

    @GetMapping("/order-list")
    public String orderList() {
        return "/order/order-list";
    }
}
