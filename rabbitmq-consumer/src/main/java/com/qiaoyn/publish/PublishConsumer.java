package com.qiaoyn.publish;

import com.qiaoyn.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author qiaoyanan
 * date:2022/10/20 14:19
 */
public class PublishConsumer implements Runnable{

    public static void main(String[] args) {
        // 定义2个线程，线程名称就用队列名称（投机取巧，避免写2个消费者实例，代码一样只是绑定的队列要不同）
        new Thread(new PublishConsumer(),"queue1").start();
        new Thread(new PublishConsumer(),"queue2").start();
    }

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        // 1.获取连接
        Connection connection = MQConnectionUtils.getConnection("消费者");

        Channel channel = null;
        try {
            // 2.通过连接建立通道
            channel = connection.createChannel();

            // 3.通过通道创建队列
            /**
             * @Params1 队列名称
             * @Params2 是否持久化    true：持久化，该队列将在服务器重启后依然继续存在
             * @Params3 是否独占队列  true：独占，仅限于此连接
             * @Params4 自动删除（最后一条消息被消费完毕后，是否把队列自动删除）
             * @Params5 队列的其他属性（构造参数）
             *
             *  面试题：所谓持久化即消息存盘，非持久化会存盘吗？ 回答：会存盘，但会随着服务器宕机而丢失
             */
            channel.queueDeclare(name, true, false, false, null);

            // 4.绑定交换机和队列的关系
            /**
             * @Params1 队列名称
             * @Params2 需绑定的交换机名称
             * @Params3 路由key，用于direct或者topic模式，通过某个routingKey绑定交换机
             */
            channel.queueBind(name, "my-exchange", "");


            // 5.消费消息
            /**  @param1：队列名称
             *   @param2：是否自动应答 true：是，消息一旦被消费成功，消息则从队列中删除
             *   @param3：消息送达时的回调
             *   @param4：消费者被取消时的回调
             */
            channel.basicConsume(name, true, (consumerTag, message) -> System.out.println("从" + name + "队列中接收消息成功！内容：" + new String(message.getBody(), "UTF-8")), consumerTag -> System.out.println("接收消息失败。。。。。"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
