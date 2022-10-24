package com.qiaoyn.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author qiaoyanan
 * date:2022/10/24 14:05
 */
@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 模拟用户创建订单
     * @param userId  客户ID
     * @param productId 产品ID
     * @param num 数量
     */
    public void createOrder(String userId, String productId, int num){
        // 1.根据商品ID查询库存是否充足

        // 2.生成订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功....");

        // 3.将订单id封装成MQ消息，投递到交换机
        /**@params1 ：交换机名称
         * @params2 ：路由key/队列名称
         * @params3 ：消息内容
         */
        rabbitTemplate.convertAndSend("fanout_order_exchange","",orderId);

    }
}
