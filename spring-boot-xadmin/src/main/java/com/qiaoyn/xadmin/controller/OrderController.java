package com.qiaoyn.xadmin.controller;

import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.OrderEntity;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.UserEntity;
import com.qiaoyn.xadmin.entity.dto.OrderQuery;
import com.qiaoyn.xadmin.entity.dto.UserQuery;
import com.qiaoyn.xadmin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String orderList(Model model, OrderQuery orderQuery, HttpSession session) {
        orderQuery.setUserName(String.valueOf(session.getAttribute("loginUser")));
        PageInfo<OrderEntityVo> orderPageInfo = orderService.queryOrderList(orderQuery);
        model.addAttribute("page", orderPageInfo);
        return "/order/order-list";
    }

    @PostMapping("/order-list")
    public String orderListByName(Model model, OrderQuery orderQuery, HttpSession session) {
        orderQuery.setUserName(String.valueOf(session.getAttribute("loginUser")));
        PageInfo<OrderEntityVo> orderPageInfo = orderService.queryOrderList(orderQuery);
        model.addAttribute("page", orderPageInfo);
        return "/order/order-list";
    }

}
