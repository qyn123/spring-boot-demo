package com.qiaoyn.direct;

import com.qiaoyn.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author qiaoyanan
 * date:2022/10/19 15:26
 */
public class RoundRobinProduce {

    public static void main(String[] args) {

        // 1.获取连接
        Connection connection = MQConnectionUtils.getConnection("生产者");

        Channel channel = null;
        try {
            // 2.通过连接建立通道
            channel = connection.createChannel();

            // 3.发送消息
            // 注意：my-exchange2是交换机名称，在图形化界面已创建好，就不用代码声明创建了
            for (int i = 1; i <= 20; i++) {
                String message = "你好 " + i + " 旧时光！";
                channel.basicPublish("my-exchange2", "", null, message.getBytes());
                Thread.sleep(i * 20);
            }
            System.out.println("消息已发送完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MQConnectionUtils.close(connection, channel);
        }
    }

}
