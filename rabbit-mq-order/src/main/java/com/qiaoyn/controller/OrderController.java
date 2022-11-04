package com.qiaoyn.controller;

import com.qiaoyn.entity.Order;
import com.qiaoyn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiaoyanan
 * date:2022/11/04 13:59
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create/order")
    public void createOrder() throws Exception {
        Order order = new Order();
        order.setOrderId(100001);
        order.setUserName("张三");
        order.setContext("方便面");
        order.setNum(1);
        orderService.createOrder(order);
    }


    @RequestMapping("/createMq/order")
    public void createMqOrder() throws Exception {
        Order order = new Order();
        order.setOrderId(100001);
        order.setUserName("张三");
        order.setContext("方便面");
        order.setNum(1);
        orderService.createMQOrder(order);
    }


    @RequestMapping("/createMq/orders")
    public void createMqOrders() throws Exception {
        Order order = new Order();
        order.setOrderId(100001);
        order.setUserName("张三");
        order.setContext("方便面");
        order.setNum(1);
        orderService.createMqOrder(order);
    }


}
