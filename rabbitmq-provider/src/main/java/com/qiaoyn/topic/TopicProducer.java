package com.qiaoyn.topic;

import com.qiaoyn.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author qiaoyanan
 * date:2022/10/24 10:04
 */
public class TopicProducer {

    public static void main(String[] args) {

        // 1.获取连接
        Connection connection = MQConnectionUtils.getConnection("生产者");

        Channel channel = null;
        try {
            // 2.通过连接建立通道
            channel = connection.createChannel();


            // 3.通过通道创建一个交换机my-exchange
            /** @params1 ：交换机名称
             *  @params2 ：交换机类型（topic主题模式）
             *  @params3 ：是否持久化 true：是，服务器重启交换机不会消失
             */
            String exchangeName = "my-exchange-topic";
            channel.exchangeDeclare(exchangeName, "topic",true);

            // 4.创建第一个队列：my-queue1
            String queue1 = "topic-queue1";
            channel.queueDeclare(queue1, true, false, false, null);
            // 5. 绑定交换机与队列的关系，指定一个绑定的key
            /** @params1 ：要绑定的队列
             *  @params2 ：要绑定到哪个交换机
             *  @params3 ：交换机和队列之间绑定了一个key，这个key就是BindingKey
             */
            String BindingKey = "*.orange.*"; // BindingKey：交换机与队列之间绑定的key
            channel.queueBind(queue1,exchangeName,BindingKey);


            // 创建第二个队列：my-queue2 （同上述4步）
            String queue2 = "topic-queue2";
            channel.queueDeclare(queue2, true, false, false, null);
            // 绑定交换机与队列my-queue2 （同上述5步）
            // 主题模式的交换机，同一个队列是可以绑定多个BindingKey的，这里给队列my-queue2绑定2个key
            channel.queueBind(queue2,exchangeName,"*.*.rabbit");
            channel.queueBind(queue2,exchangeName,"#.lazy");


            // 6.发送消息给队列
            /** @params1 ：消息发送到哪个交换机
             *  @params2 ：路由key，这个交换机可能绑定了很多队列，但这条消息我不想发给该交换机下全部的队列
             *             那么我需要指定一个RoutingKey，用来识别消息最终进入哪个队列
             *   ————上述队列与交换机的绑定中有指定BindingKey，只有消息的RoutingKey与BindingKey模糊匹配上，交换机才会把消息发给该队列
             *  @params3 ：消息内容
             */
            String routingKey = "my.lazy"; // 投递消息时指定的RoutingKey
            channel.basicPublish(exchangeName, routingKey, null, "你好 旧时光！".getBytes());
            String routingKey1 = "my.my.rabbit"; // 投递消息时指定的RoutingKey
            channel.basicPublish(exchangeName, routingKey1, null, "你好 旧时光！".getBytes());
            String routingKey2 = "my.orange.my"; // 投递消息时指定的RoutingKey
            channel.basicPublish(exchangeName, routingKey2, null, "你好 旧时光！".getBytes());
            System.out.println("消息生产完毕！");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MQConnectionUtils.close(connection,channel);
        }
    }
}
