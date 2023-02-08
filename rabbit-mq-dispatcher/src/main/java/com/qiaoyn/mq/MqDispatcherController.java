package com.qiaoyn.mq;

import com.qiaoyn.entity.Order;
import com.qiaoyn.service.DispatchService;
import com.rabbitmq.client.AMQP;
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
public class MqDispatcherController {

    @Autowired
    private DispatchService dispatchService;

    private int count = 1;

    @RabbitListener(queues = {"order_confirm_fanoutQueue"})
    public void  messageConsumer(String orderMsg, @Header(AmqpHeaders.DELIVERY_TAG) long tag, Channel channel) throws Exception {
        try {
            // 获取消息队列的消息
            System.out.println("运单队列收到mq的消息是："+ orderMsg + ",count=" + count++);
            JSONObject jsonObject = JSONObject.fromObject(orderMsg);
            Order order = (Order) JSONObject.toBean(jsonObject, Order.class);
            // 手动ack告诉mq消息已经正常消费
            /**
             * 可靠消费重试机制： 重试机制会引发死循环
             * 解决方案：
             * 1.控制重试次数 （达到重试次数会移除消息，会出现消息丢失，建议用死信队列做备份）
             * 2. try catch + 手动ack
             * 3. try catch + 手动ack + 死信队列
             */
            System.out.println(1 / 0);;
            dispatchService.dispatch(String.valueOf(order.getOrderId()));

            channel.basicAck(tag,false);
        } catch (Exception e) {
            // 如果出现异常的情况，根据实际的情况进行重发
            // 参数1：消息的tag 参数2：false ,多条处理   参数3：requeue 重发
            // false: 不会重发，会把消息打入到死信队列里
            // true： 会死循环的重发，建议如果使用true的话，不要加try catch ，否则就会造成死循环
            channel.basicNack(tag,false,false); //死信队列
        }
    }
}
