package com.qiaoyn.service;

import com.qiaoyn.dao.OrderDao;
import com.qiaoyn.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * @author qiaoyanan
 * date:2022/11/04 11:50
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MqService mqService;


    // 订单创建，整个方法添加事物
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order) throws Exception {

        // 1.订单信息--插入订单系统，订单数据库事物
        orderDao.saveOrder(order);

        // 2.通过http接口发送订单信息到运单系统
        String result = dispatchHttpApi(order.getOrderId());
        if(!"success".equals(result)){
            throw new Exception("订单系统创建失败，原因是运单接口调用失败！");
        }
    }


    // 模拟http请求接口发送，运单系统，将订单号传过去
    public String dispatchHttpApi(int orderId){

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // 连接超时时间，连接上服务器(握手成功)的时间，超出抛出connect timeout
        // 3秒
        factory.setConnectTimeout(3000);

        // 处理超时：数据读取超时时间(毫秒)，服务器返回数据(response)的时间，超过抛出read timeout
        // 2秒
        factory.setReadTimeout(2000);

        // 发送http请求
        String url = "http://localhost:9000/dispatch/order?orderId="+orderId;

        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate.getForObject(url,String.class);
    }


    public void createMQOrder(Order order) throws Exception {
        // 1.下单
        orderDao.saveOrder(order);
        // 2.通过mq分发消息
        mqService.pushMessage(order);
    }

    public void createMqOrder(Order order) throws Exception {
        // 1.下单
        orderDao.saveMqOrder(order);
        // 2.通过mq分发消息
        mqService.pushMessage(order);
    }


}
