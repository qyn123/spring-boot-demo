package com.qiaoyn.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author qiaoyanan
 * date:2022/10/19 15:20
 */
public class MQConnectionUtils {
    // 获取连接
    public static Connection getConnection(String connectionName){
        Connection connection = null;

        // 1.建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            // 2.通过连接工厂建立连接
            connection = factory.newConnection(connectionName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 释放资源
    public static void close(Connection connection, Channel channel){
        // 1.关闭通道
        if(channel!=null && channel.isOpen()){
            try {
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 2.关闭连接
        if(connection!=null){
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
