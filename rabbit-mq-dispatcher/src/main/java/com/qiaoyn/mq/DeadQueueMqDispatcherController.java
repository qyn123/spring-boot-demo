package com.qiaoyn.mq;

import com.qiaoyn.entity.Order;
import com.qiaoyn.service.DispatchService;
import com.rabbitmq.client.Channel;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @author qiaoyanan
 * date:2023/02/04 15:26
 */
@Service
public class DeadQueueMqDispatcherController {

    @Autowired
    private DispatchService dispatchService;

    private int count = 1;

    @RabbitListener(queues = {"dead.fanout.queue"})
    public void  messageConsumer(String orderMsg, @Header(AmqpHeaders.DELIVERY_TAG) long tag, Channel channel) throws Exception {
        try {
            // 获取消息队列的消息
            System.out.println("收到mq的消息是："+ orderMsg + ",count=" + count++);
            JSONObject jsonObject = JSONObject.fromObject(orderMsg);
            Order order = (Order) JSONObject.toBean(jsonObject, Order.class);
            dispatchService.dispatchs(String.valueOf(order.getOrderId()));
            // 手动ack告诉mq消息已经正常消费
            /**
             * 可靠消费重试机制： 重试机制会引发死循环
             * 解决方案：
             * 1.控制重试次数 （达到重试次数会移除消息，会出现消息丢失，建议用死信队列做备份）
             * 2. try catch + 手动ack
             * 3. try catch + 手动ack + 死信队列
             */
            channel.basicAck(tag,false);
        } catch (Exception e) {
            System.out.println("人工干预");
            System.out.println("发短信预警");
            System.out.println("将消息存储到别的DB："+ tag);
            //死信队列
            channel.basicNack(tag,false,false);
        }
    }
}
