package com.qiaoyn.xadmin.controller;

import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.dto.OrderQuery;
import com.qiaoyn.xadmin.mapper.ConfigMapper;
import com.qiaoyn.xadmin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author qiaoyanan
 * date:2022/10/08 16:52
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ConfigMapper configMapper;

    @GetMapping("/order-add")
    public String orderAdd() {
        return "/order/order-add";
    }

    @GetMapping("/order-list")
    public String orderList(Model model, OrderQuery orderQuery, HttpSession session) {
        orderQuery.setUserName(String.valueOf(session.getAttribute("loginUser")));
        System.out.println("orderQuery.getPayStatus()===================>"+orderQuery.getPayStatus());
        model.addAttribute("payStatusList", configMapper.queryList("payStatus"));
        model.addAttribute("payMethodList", configMapper.queryList("payMethod"));
        model.addAttribute("orderStatusList", configMapper.queryList("orderStatus"));
        PageInfo<OrderEntityVo> orderPageInfo = orderService.queryOrderList(orderQuery);
        model.addAttribute("page", orderPageInfo);
        return "/order/order-list";
    }

    @PostMapping("/order-list")
    public String orderListByName(Model model, OrderQuery orderQuery, HttpSession session) {
        orderQuery.setUserName(String.valueOf(session.getAttribute("loginUser")));
        System.out.println("orderQuery.getPayStatus()===================>"+orderQuery.getPayStatus());
        model.addAttribute("payStatusList", configMapper.queryList("payStatus"));
        model.addAttribute("payMethodList", configMapper.queryList("payMethod"));
        model.addAttribute("orderStatusList", configMapper.queryList("orderStatus"));
        PageInfo<OrderEntityVo> orderPageInfo = orderService.queryOrderList(orderQuery);
        model.addAttribute("page", orderPageInfo);

        return "/order/order-list";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        boolean b = orderService.deleteUserById(id);
        if (b) {
            return "redirect:/order-list";
        } else {
            return "redirect:/order-list";
        }
    }

}
