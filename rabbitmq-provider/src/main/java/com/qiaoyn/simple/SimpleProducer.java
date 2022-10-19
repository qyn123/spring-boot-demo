package com.qiaoyn.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author qiaoyanan
 * date:2022/10/19 14:30
 */
public class SimpleProducer {


    public static void main(String[] args) {

        // 1.建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 基于TCP/IP协议自然而然需要端口、ip等，比如mysql，redis等中间件
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

            // 4.通过通道创建交换机，声明队列，绑定关系，路由key，发送消息，和接收消息
            String queueName = "queue001";
            /**
             * @Params1 队列名称
             * @Params2 是否持久化    true：持久化，该队列将在服务器重启后依然继续存在
             * @Params3 是否独占队列  true：独占，仅限于此连接
             * @Params4 自动删除（最后一条消息被消费完毕后，是否把队列自动删除）
             * @Params5 队列的其他属性（构造参数）
             *
             *  面试题：所谓持久化即消息存盘，非持久化会存盘吗？ 回答：会存盘，但会随着服务器宕机而丢失
             */
            channel.queueDeclare(queueName, true, false, false, null);

            // 5.准备消息内容
            String message = "hello, 旧时光！";

            // 6.发送消息给队列
            /**  @param1：交换机 面试题：可以存在没有交换机的队列吗？ 不可能，虽未指定但会存在默认的交换机
             *   @param2：队列，路由key
             *   @param3：消息的状态控制
             *   @param4：消息主题
             */
            channel.basicPublish("", queueName, null, message.getBytes());

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
