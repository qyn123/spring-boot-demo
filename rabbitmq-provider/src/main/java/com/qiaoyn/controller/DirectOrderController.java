package com.qiaoyn.controller;

import com.qiaoyn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiaoyanan
 * date:2022/10/24 14:08
 */
@RestController
@RequestMapping("/direct")
public class DirectOrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/order")
    public void getOrder() {
        orderService.createDirectOrder("1001","10",12);
    }
}
