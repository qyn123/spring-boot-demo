package com.qiaoyn.receiver.dead;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 死信队列消费者:https://blog.csdn.net/w15558056319/article/details/123505899
 * 如何判断一条消息是死信？
 *   1)消息被拒绝 2)消息过期 3)队列达到最大长度
 * @author qiaoyanan
 * date:2022/10/24 15:23
 */
@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "dead_direct_Queue1",durable = "true",exclusive = "false",autoDelete = "false")
        ,exchange = @Exchange(value = "dead_direct_exchange",type = ExchangeTypes.DIRECT,durable = "true",autoDelete = "false")
        ,key = "dead"
))
public class DeadDirectConsumer {

    // 接收消息
    @RabbitHandler
    public void receiveMess(String message){
        log.info("死信队列接收到订单消息:[{}]",message);
    }
}