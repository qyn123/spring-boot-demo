package com.qiaoyn.direct;

import com.qiaoyn.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author qiaoyanan
 * date:2022/10/19 15:29
 */
public class RoundRobinConsumerB implements Runnable {
    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        // 1.获取连接
        Connection connection = MQConnectionUtils.getConnection("消费者");

        Channel channel;
        try {
            // 2.通过连接建立通道
            channel = connection.createChannel();

            // 3.创建一个消费者，消费消息
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(name + "接收成功！消息内容：" + new String(body, "UTF-8"));
                    try {
                        // 假设消费者1处理消息速度要1秒
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            // 注意：my-queue是队列名称，在图形化界面已创建好，并绑定了my-exchange2交换机
            channel.basicConsume("my-queue", true, consumer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //不关闭，为了展示多个消费者实时接收消息
            // MQConnectionUtils.close(connection,channel);
        }
    }
}
