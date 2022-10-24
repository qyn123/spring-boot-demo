package com.qiaoyn.receiver.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author qiaoyanan
 *
 * @QueueBinding (队列，交换机，交换机与队列的BindingKey)
 *     声明创建队列 qq_direct_Queue
 *     声明创建交换机direct_order_exchange（不存在则创建，不会多次创建）
 *     绑定交换机与队列的关系，BindingKey = “qq”
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "qq_direct_Queue",durable = "true",exclusive = "false",autoDelete = "false")
        ,exchange = @Exchange(value = "direct_order_exchange",type = ExchangeTypes.DIRECT,durable = "true",autoDelete = "false")
        ,key = "qq"
))
public class DirectQqConsumer {
    // 接收消息
    @RabbitHandler
    public void receiveMess(String message){
        System.out.println("QqConsumer direct 接收到订单消息————>"+message);
    }
}
