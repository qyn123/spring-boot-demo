package com.qiaoyn.receiver.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author qiaoyanan
 * date:2022/10/24 15:27
 *  @QueueBinding (队列 ， 交换机 ， 交换机与队列的BindingKey)
 *  声明创建队列 sms_direct_Queue
 *  声明创建交换机direct_order_exchange（不存在则创建，不会多次创建）
 *  绑定交换机与队列的关系，BindingKey = “sms”

 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "sms_direct_Queue", durable = "true", exclusive = "false", autoDelete = "false")
        , exchange = @Exchange(value = "direct_order_exchange", type = ExchangeTypes.DIRECT, durable = "true", autoDelete = "false")
        , key = "sms"
))
public class DirectSmsConsumer {
    // 接收消息
    @RabbitHandler
    public void receiveMess(String message) {
        System.out.println("SmsConsumer direct 接收到订单消息————>" + message);
    }

}
