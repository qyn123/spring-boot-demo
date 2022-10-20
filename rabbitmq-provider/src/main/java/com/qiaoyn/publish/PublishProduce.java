package com.qiaoyn.publish;

import com.qiaoyn.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 发布订阅模式:https://blog.csdn.net/w15558056319/article/details/123401126
 * @author qiaoyanan
 * date:2022/10/20 14:18
 */
public class PublishProduce {

    public static void main(String[] args) {
        // 1.获取连接
        Connection connection = MQConnectionUtils.getConnection("生产者");
        Channel channel = null;
        try {
            // 2.通过连接建立通道
            channel = connection.createChannel();

            String exchangeName = "my-exchange";
            // 3.通过通道创建交换机 (第一个参数为交换机名称，第二个参数为交换机的类型,第三个参数durable持久化)
            channel.exchangeDeclare(exchangeName, "fanout",true);

            // 4.发送消息到交换机
            String message = "你好 旧时光！";
            channel.basicPublish(exchangeName, "", null, message.getBytes());
            System.out.println("消息生产成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MQConnectionUtils.close(connection,channel);
        }
    }
}
