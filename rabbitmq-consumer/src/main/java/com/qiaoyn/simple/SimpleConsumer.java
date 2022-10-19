package com.qiaoyn.simple;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author qiaoyanan
 * date:2022/10/19 14:32
 */
public class SimpleConsumer {

    public static void main(String[] args) {
        // 1.建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = null;
        Channel channel = null;
        try {
            // 2.通过连接工厂建立连接
            connection = factory.newConnection("我的第一个RabbitMQ Demo");

            // 3.通过连接建立通道
            channel = connection.createChannel();

            // 4.消费消息
            /**  @param1：消费的队列名称
             *   @param2：是否自动应答 true：是，消息一旦被消费成功，消息则从队列中删除
             *   @param3：消息送达时的回调
             *   @param4：消费者被取消时的回调
             */
            channel.basicConsume("queue001", true, (consumerTag, message) -> System.out.println("接收成功！消息内容：" + new String(message.getBody(), "UTF-8")), new CancelCallback() {
                public void handle(String consumerTag) throws IOException {
                    System.out.println("接收消息失败。。。。。");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7.关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 8.关闭连接
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
