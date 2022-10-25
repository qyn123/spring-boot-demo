package com.qiaoyn.controller;

import com.qiaoyn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiaoyanan
 * date:2022/10/25 17:50
 */
@RestController
@RequestMapping("ttl")
public class TtlOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public void getOrder() {
        orderService.createTtlOrder("1001","10",12);
    }


    @GetMapping("/message/order")
    public void getMessageOrder() {
        orderService.createTtlMessageOrder("1001","10",12);
    }
}
