package com.qiaoyn.direct;

import com.qiaoyn.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author qiaoyanan
 * date:2022/10/19 16:04
 */
public class FairConsumerB implements Runnable{


    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        // 1.获取连接
        Connection connection = MQConnectionUtils.getConnection("消费者");

        Channel channel;
        try {
            // 2.通过连接建立通道
            channel = connection.createChannel();

            // 3.设置不公平分发：限制发送给同一个消费者 不得超过一条消息，处理完毕再分发
            channel.basicQos(1);

            // 3.创建一个消费者，消费消息
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(name + "接收成功！消息内容：" + new String(body, "UTF-8"));
                    try {
                        // 假设消费者1处理消息速度要1秒
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 5.手动确认，消息已被消费
                    //（由于basicQos设置了1，这里手动确认已被消费后，下一条消息才会进来）
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            // 注意：my-queue是队列名称，在图形化界面已创建好，并绑定了my-exchange2交换机
            channel.basicConsume("my-queue", false, consumer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //不关闭，为了展示多个消费者实时接收消息
            // MQConnectionUtils.close(connection,channel);
        }
    }
}
