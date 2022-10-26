package com.qiaoyn.controller;

import com.qiaoyn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 死信队列:消息过期
 * @author qiaoyanan
 * date:2022/10/25 17:50
 */
@RestController
@RequestMapping("/dead")
public class DeadTtlOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public void getDeadTtlOrder() {
        orderService.createDeadTtlOrderQueue("1001","10",12);
    }

}
