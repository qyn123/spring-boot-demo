package com.qiaoyn.service;

import com.qiaoyn.entity.Order;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author qiaoyanan
 * date:2022/11/04 14:25
 */
@Service
public class MqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 投递订单信息到mq队列
    public void pushMessage(Order order){
        // 将订单对象转成json
        String objStr = JSONObject.fromObject(order).toString();
        // 投递消息到交换机中
        rabbitTemplate.convertAndSend("order_confirm_fanoutExchange","",objStr,new CorrelationData(order.getOrderId()+""));
    }


    // publisher生产者确认
    /**
     * 注解好多人以为是Spring提供的，其实是java自己的注解
     * @PostConstruct ：用来修饰一个非静态的void方法，在服务器加载servlet时运行，
    PostConstruct在构造函数之后执行，init()方法前执行
     * 作用：在当前对象加载完依赖注入的bean后，运行该注解修饰的方法，而且只运行一次。
     * （该注解可以用来当作一些初始化的处理）
     */
    @PostConstruct
    public void regCallback(){
        // 注意：下面内容也可放到消息投递的方法里，为了代码层次感，因此封装为单独的方法
        // 生产者确认机制：消息发送成功后，给予生产者的消息回执，来确保生产者的可靠性
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                // 如果ack为true 代表消息已投递成功
                String orderId = correlationData.getId();
                if(!ack){
                    // 消息投递失败了，这里可能要进行其他方式存储
                    System.out.println("MQ队列应答失败，orderId是："+orderId);
                    return;
                }

                // 应答成功业务逻辑
                try {
                    // 投递成功：冗余消息表该订单状态改为1，表示该条消息已成功投递到队列中去
                    int count = jdbcTemplate.update("update message_order set status=1 where orderId=?", orderId);
                    if(count==1){
                        System.out.println("本地消息状态修改成功，消息成功投递到消息队列......");
                    }
                }catch (Exception e){
                    System.out.println("本地消息状态修改失败，原因："+e.getMessage());
                }
            }
        });
    }
}
