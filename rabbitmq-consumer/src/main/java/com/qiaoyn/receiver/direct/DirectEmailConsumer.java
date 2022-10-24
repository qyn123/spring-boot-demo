package com.qiaoyn.receiver.direct;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @QueueBinding (队列，交换机，交换机与队列的BindingKey)
 *    声明创建队列 email_direct_Queue
 *    声明创建交换机direct_order_exchange
 *    绑定交换机与队列的关系，BindingKey = “email”
 * @author qiaoyanan
 * date:2022/10/24 15:23
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "email_direct_Queue",durable = "true",exclusive = "false",autoDelete = "false")
        ,exchange = @Exchange(value = "direct_order_exchange",type = ExchangeTypes.DIRECT,durable = "true",autoDelete = "false")
        ,key = "email"
))
public class DirectEmailConsumer {

    // 接收消息
    @RabbitHandler
    public void receiveMess(String message){
        System.out.println("EmailConsumer direct 接收到订单消息————>"+message);
    }
}