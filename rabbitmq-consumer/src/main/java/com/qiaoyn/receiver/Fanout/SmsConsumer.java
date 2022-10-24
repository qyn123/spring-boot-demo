package com.qiaoyn.receiver.Fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *  @RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
 *  @RabbitListener 标注在类上面表示当有收到消息的时候，就交给@RabbitHandler的方法处理，
 *                  具体使用哪个方法处理，根据 MessageConverter转换后的参数类型
 *                 （message的类型匹配到哪个方法就交给哪个方法处理）
 * @author qiaoyanan
 * date:2022/10/24 14:15
 */
@Component
// 监听email_fanout_Queue队列
@RabbitListener(queues = {"sms_fanout_Queue"})
public class SmsConsumer {

    //接收消息
    @RabbitHandler
    public void receiver(String message) {
        System.out.println("SmsConsumer 接收到订单消息————>"+message);
    }
}
