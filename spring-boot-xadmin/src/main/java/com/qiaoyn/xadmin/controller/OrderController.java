package com.qiaoyn.xadmin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.ConfigEntity;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.dto.OrderQuery;
import com.qiaoyn.xadmin.mapper.ConfigMapper;
import com.qiaoyn.xadmin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaoyanan
 * date:2022/10/08 16:52
 */
@Controller
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ConfigMapper configMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/order-add")
    public String orderAdd() {
        return "/order/order-add";
    }

    @GetMapping("/order-list")
    public String orderList(Model model, OrderQuery orderQuery, HttpSession session) {
        orderQuery.setUserName(String.valueOf(session.getAttribute("loginUser")));
        log.info("==============================支付状态开始================================");
        List<ConfigEntity> payStatusList;
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get("payStatusKey"))) {
            log.info("走的数据库");
            payStatusList = configMapper.queryList("payStatus");
            String s = JSON.toJSON(payStatusList).toString();
            redisTemplate.opsForValue().set("payStatusKey", s, 1800, TimeUnit.SECONDS);
        } else {
            log.info("走的Redis");
            String string = String.valueOf(redisTemplate.opsForValue().get("payStatusKey"));
            log.info("支付状态:{}", string);
            payStatusList = JSONObject.parseArray(string, ConfigEntity.class);
        }
        model.addAttribute("payStatusList", payStatusList);
        log.info("==============================支付状态结束================================");
        log.info("==============================支付方式开始================================");
        List<ConfigEntity> payMethodList;
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get("payMethodKey"))) {
            log.info("走的数据库");
            payMethodList = configMapper.queryList("payMethod");
            String s = JSON.toJSON(payStatusList).toString();
            redisTemplate.opsForValue().set("payMethodKey", s, 1800, TimeUnit.SECONDS);
        } else {
            log.info("走的Redis");
            String string = String.valueOf(redisTemplate.opsForValue().get("payMethodKey"));
            log.info("支付方式:{}", string);
            payMethodList = JSONObject.parseArray(string, ConfigEntity.class);
        }
        model.addAttribute("payMethodList", payMethodList);
        log.info("==============================支付方式结束================================");
        log.info("==============================订单状态开始================================");
        List<ConfigEntity> orderStatusList;
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get("orderStatusKey"))) {
            log.info("走的数据库");
            orderStatusList = configMapper.queryList("orderStatus");
            String s = JSON.toJSON(payStatusList).toString();
            redisTemplate.opsForValue().set("orderStatusKey", s, 1800, TimeUnit.SECONDS);
        } else {
            log.info("走的Redis");
            String string = String.valueOf(redisTemplate.opsForValue().get("orderStatusKey"));
            log.info("订单状态:{}", string);
            orderStatusList = JSONObject.parseArray(string, ConfigEntity.class);
        }
        model.addAttribute("orderStatusList", orderStatusList);
        log.info("==============================订单状态结束================================");
        PageInfo<OrderEntityVo> orderPageInfo = orderService.queryOrderList(orderQuery);
        model.addAttribute("page", orderPageInfo);
        return "/order/order-list";
    }

    @PostMapping("/order-list")
    public String orderListByName(Model model, OrderQuery orderQuery, HttpSession session) {
        orderQuery.setUserName(String.valueOf(session.getAttribute("loginUser")));
        log.info("==============================支付状态开始================================");
        List<ConfigEntity> payStatusList;
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get("payStatusKey"))) {
            log.info("走的数据库");
            payStatusList = configMapper.queryList("payStatus");
            String s = JSON.toJSON(payStatusList).toString();
            redisTemplate.opsForValue().set("payStatusKey", s, 1800, TimeUnit.SECONDS);
        } else {
            log.info("走的Redis");
            String string = String.valueOf(redisTemplate.opsForValue().get("payStatusKey"));
            log.info("支付状态:{}", string);
            payStatusList = JSONObject.parseArray(string, ConfigEntity.class);
        }
        model.addAttribute("payStatusList", payStatusList);
        log.info("==============================支付状态结束================================");
        log.info("==============================支付方式开始================================");
        List<ConfigEntity> payMethodList;
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get("payMethodKey"))) {
            log.info("走的数据库");
            payMethodList = configMapper.queryList("payMethod");
            String s = JSON.toJSON(payStatusList).toString();
            redisTemplate.opsForValue().set("payMethodKey", s, 1800, TimeUnit.SECONDS);
        } else {
            log.info("走的Redis");
            String string = String.valueOf(redisTemplate.opsForValue().get("payMethodKey"));
            log.info("支付方式:{}", string);
            payMethodList = JSONObject.parseArray(string, ConfigEntity.class);
        }
        model.addAttribute("payMethodList", payMethodList);
        log.info("==============================支付方式结束================================");
        log.info("==============================订单状态开始================================");
        List<ConfigEntity> orderStatusList;
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get("orderStatusKey"))) {
            log.info("走的数据库");
            orderStatusList = configMapper.queryList("orderStatus");
            String s = JSON.toJSON(payStatusList).toString();
            redisTemplate.opsForValue().set("orderStatusKey", s, 1800, TimeUnit.SECONDS);
        } else {
            log.info("走的Redis");
            String string = String.valueOf(redisTemplate.opsForValue().get("orderStatusKey"));
            log.info("订单状态:{}", string);
            orderStatusList = JSONObject.parseArray(string, ConfigEntity.class);
        }
        model.addAttribute("orderStatusList", orderStatusList);
        log.info("==============================订单状态结束================================");
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
