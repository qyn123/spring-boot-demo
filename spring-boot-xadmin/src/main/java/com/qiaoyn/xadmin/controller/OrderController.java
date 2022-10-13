package com.qiaoyn.xadmin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.ConfigEntity;
import com.qiaoyn.xadmin.entity.OrderEntity;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.UserEntity;
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
    public String orderAdd(Model model) {
        log.info("==============================支付状态开始================================");
        List<ConfigEntity> payStatusList = getCatchConfigList("payStatusKey", "payStatus", 3600, TimeUnit.SECONDS);
        model.addAttribute("payStatusList", payStatusList);
        log.info("==============================支付状态结束================================");
        log.info("==============================支付方式开始================================");
        List<ConfigEntity> payMethodList = getCatchConfigList("payMethodKey", "payMethod", 3600, TimeUnit.SECONDS);
        model.addAttribute("payMethodList", payMethodList);
        log.info("==============================支付方式结束================================");
        log.info("==============================订单状态开始================================");
        List<ConfigEntity> orderStatusList = getCatchConfigList("orderStatusKey", "orderStatus", 3600, TimeUnit.SECONDS);
        model.addAttribute("orderStatusList", orderStatusList);
        log.info("==============================订单状态结束================================");
        log.info("==============================发货状态开始================================");
        List<ConfigEntity> deliveryStatusList = getCatchConfigList("deliveryStatusKey", "deliveryStatus", 3600, TimeUnit.SECONDS);
        model.addAttribute("deliveryStatusList", deliveryStatusList);
        log.info("==============================发货状态结束================================");
        log.info("==============================配送方式开始================================");
        List<ConfigEntity> sendMethodList = getCatchConfigList("sendMethodKey", "sendMethod", 3600, TimeUnit.SECONDS);
        model.addAttribute("sendMethodList", sendMethodList);
        log.info("==============================配送方式结束================================");
        return "/order/order-add";
    }

    @GetMapping("/order-list")
    public String orderList(Model model, OrderQuery orderQuery) {
        log.info("==============================支付状态开始================================");
        List<ConfigEntity> payStatusList = getCatchConfigList("payStatusKey", "payStatus", 3600, TimeUnit.SECONDS);
        model.addAttribute("payStatusList", payStatusList);
        log.info("==============================支付状态结束================================");
        log.info("==============================支付方式开始================================");
        List<ConfigEntity> payMethodList = getCatchConfigList("payMethodKey", "payMethod", 3600, TimeUnit.SECONDS);
        model.addAttribute("payMethodList", payMethodList);
        log.info("==============================支付方式结束================================");
        log.info("==============================订单状态开始================================");
        List<ConfigEntity> orderStatusList = getCatchConfigList("orderStatusKey", "orderStatus", 3600, TimeUnit.SECONDS);
        model.addAttribute("orderStatusList", orderStatusList);
        log.info("==============================订单状态结束================================");
        PageInfo<OrderEntityVo> orderPageInfo = orderService.queryOrderList(orderQuery);
        model.addAttribute("page", orderPageInfo);
        return "/order/order-list";
    }

    @PostMapping("/order-list")
    public String orderListByName(Model model, OrderQuery orderQuery) {
        log.info("==============================支付状态开始================================");
        List<ConfigEntity> payStatusList = getCatchConfigList("payStatusKey", "payStatus", 3600, TimeUnit.SECONDS);
        model.addAttribute("payStatusList", payStatusList);
        log.info("==============================支付状态结束================================");
        log.info("==============================支付方式开始================================");
        List<ConfigEntity> payMethodList = getCatchConfigList("payMethodKey", "payMethod", 3600, TimeUnit.SECONDS);
        model.addAttribute("payMethodList", payMethodList);
        log.info("==============================支付方式结束================================");
        log.info("==============================订单状态开始================================");
        List<ConfigEntity> orderStatusList = getCatchConfigList("orderStatusKey", "orderStatus", 3600, TimeUnit.SECONDS);
        model.addAttribute("orderStatusList", orderStatusList);
        log.info("==============================订单状态结束================================");
        PageInfo<OrderEntityVo> orderPageInfo = orderService.queryOrderList(orderQuery);
        model.addAttribute("page", orderPageInfo);
        return "/order/order-list";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes attributes) {
        boolean b = orderService.deleteUserById(id);
        if (b) {
            return "redirect:/order-list";
        } else {
            return "redirect:/order-list";
        }
    }

    /**
     * 下拉列表缓存
     *
     * @param catchKey  缓存key
     * @param group     查询组
     * @param timeStamp 时间戳
     * @param timeUnit  时间单位
     */
    private List<ConfigEntity> getCatchConfigList(String catchKey, String group, long timeStamp, TimeUnit timeUnit) {
        List<ConfigEntity> list;
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get(catchKey))) {
            log.info("走的数据库");
            list = configMapper.queryList(group);
            String s = JSON.toJSON(list).toString();
            redisTemplate.opsForValue().set(catchKey, s, timeStamp, timeUnit);
        } else {
            log.info("走的Redis");
            String string = String.valueOf(redisTemplate.opsForValue().get(catchKey));
            log.info("支付状态:{}", string);
            list = JSONObject.parseArray(string, ConfigEntity.class);
        }
        return list;
    }

    @PostMapping("/edit-order")
    public String editOrder(OrderEntity orderEntity, RedirectAttributes attributes) {
        System.out.println(orderEntity);
        boolean b = orderService.addOrder(orderEntity);
        if (b) {
            return "redirect:/order-list";
        } else {
            return "redirect:/order-list";
        }
    }

}
