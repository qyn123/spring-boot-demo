package com.qiaoyn.xadmin.controller;

import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.OrderEntity;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.UserEntity;
import com.qiaoyn.xadmin.entity.dto.UserQuery;
import com.qiaoyn.xadmin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/10/08 16:52
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order-add")
    public String orderAdd() {
        return "/order/order-add";
    }

    @GetMapping("/order-list")
    public String orderList(Model model) {
        List<OrderEntityVo> orderEntityList = orderService.queryOrderList();
        model.addAttribute("orderEntityList", orderEntityList);
        return "/order/order-list";
    }

}
