package com.qiaoyn.routing;

import com.qiaoyn.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模式:https://blog.csdn.net/w15558056319/article/details/123403121
 * @author qiaoyanan
 * date:2022/10/20 15:51
 */
public class RoutingProduce {

    public static void main(String[] args) {

        // 1.获取连接
        Connection connection = MQConnectionUtils.getConnection("生产者");

        Channel channel = null;
        try {
            // 2.通过连接建立通道
            channel = connection.createChannel();


            // 3.通过通道创建一个交换机my-exchange，第一个参数为交换机名称，第二个参数为交换机类型
            String exchangeName = "routing-exchange";
            channel.exchangeDeclare(exchangeName, "direct",true);

            // 4.通过通道创建2个队列：my-queue1、my-queue2
            String queue1 = "my-queue1";
            channel.queueDeclare(queue1, true, false, false, null);
            String queue2 = "my-queue2";
            channel.queueDeclare(queue2, true, false, false, null);

            // 5. 指定BindingKey，绑定交换机my-exchange 和 队列my-queue1、my-queue2的关系
            String BindingKey = "email";
            /** @params1 ：要绑定的队列
             *  @params2 ：要绑定到哪个交换机
             *  @params3 ：交换机和队列之间绑定了一个key，这个key就是BindingKey
             */
            channel.queueBind(queue1,exchangeName,BindingKey);
            String BindingKey2 = "phone";
            channel.queueBind(queue2,exchangeName,BindingKey2);


            // 6.发送消息给队列
            /** @params1 ：消息发送到哪个交换机
             *  @params2 ：路由key，这个交换机可能绑定了很多队列，但这条消息我不想发给该交换机下全部的队列
             *             那么我需要指定一个RoutingKey，用来识别消息最终进入哪个队列
             *   ————上述队列与交换机的绑定中有指定BindingKey，只有消息的RoutingKey与BindingKey相同时，交换机才会把消息发给该队列
             *  @params3 ：消息内容
             */
            String routingKey = "phone";
            channel.basicPublish(exchangeName, routingKey, null, "你好 旧时光！".getBytes());
            System.out.println("消息生产完毕！");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MQConnectionUtils.close(connection,channel);
        }
    }
}
