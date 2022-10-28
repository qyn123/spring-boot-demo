package com.qiaoyn.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
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
    public void createFanoutOrder(String userId, String productId, int num){
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



    /**
     * 模拟用户创建订单
     * @param userId  客户ID
     * @param productId 产品ID
     * @param num 数量
     */
    public void createDirectOrder(String userId, String productId, int num){
        // 1.根据商品ID查询库存是否充足

        // 2.生成订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功....");

        // 3.将订单id封装成MQ消息，投递到交换机
        /**@params1 ：交换机名称
         * @params2 ：路由key/队列名称
         * @params3 ：消息内容
         * 注：指定RoutingKey=qq和email
         * 交换机direct_order_exchange与绑定的队列的BindingKey匹配的队列才会接收到
         */
        rabbitTemplate.convertAndSend("direct_order_exchange","qq",orderId);
        rabbitTemplate.convertAndSend("direct_order_exchange","email",orderId);

    }


    // 模拟用户下单
    public void createTtlOrder(String userId, String productId, int num){
        // 1.根据商品ID查询库存是否充足
        // 2.生成订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功....");

        // 3.将订单id封装成MQ消息，投递到交换机
        /**@params1 ：交换机名称
         * @params2 ：投递消息指定的RoutingKey路由键或者队列名称
         * @params3 ：消息内容
         */
        rabbitTemplate.convertAndSend("direct_ttl_exchange","ttl",orderId);
    }


    // 模拟用户下单
    public void createTtlMessageOrder(String userId, String productId, int num){
        // 1.根据商品ID查询库存是否充足
        // 2.生成订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功....");
        MessagePostProcessor  messagePostProcessor = message -> {
            // 设置消息的编码格式
            message.getMessageProperties().setContentEncoding("UTF-8");
            // 设置消息5秒后过期
            message.getMessageProperties().setExpiration("5000");
            // 设置消息优先级 （优先级分为消息优先级和队列优先级）
            // ——队列优先级高的会先被处理，消息优先级高的会先被消费
            message.getMessageProperties().setPriority(5);
            // 设置消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        };

        // 3.将订单id封装成MQ消息，投递到交换机
        /**@params1 ：交换机名称
         * @params2 ：投递消息指定的RoutingKey路由键或者队列名称
         * @params3 ：消息内容
         */
        rabbitTemplate.convertAndSend("direct_ttl_exchange","message_ttl",orderId,messagePostProcessor);
    }



    /**
     * 模拟用户创建订单
     * @param userId  客户ID
     * @param productId 产品ID
     * @param num 数量
     */

    public void createDeadTtlOrderQueue(String userId, String productId, int num){
        // 1.根据商品ID查询库存是否充足

        // 2.生成订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功....");

        // 3.将订单id封装成MQ消息，投递到交换机
        /**@params1 ：交换机名称
         * @params2 ：RoutingKey路由键/队列名称
         * @params3 ：消息内容
         */
        rabbitTemplate.convertAndSend("dead_direct_ttl_exchange","ttl",orderId);
    }


    public void createMaxLengthDeadTtlOrderQueue(String userId, String productId, int num){
        // 1.根据商品ID查询库存是否充足

        // 2.生成订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功....");

        // 3.将订单id封装成MQ消息，投递到交换机
        /**@params1 ：交换机名称
         * @params2 ：RoutingKey路由键/队列名称
         * @params3 ：消息内容
         */
        rabbitTemplate.convertAndSend("max_length_dead_direct_ttl_exchange","max.ttl",orderId);
    }



}
