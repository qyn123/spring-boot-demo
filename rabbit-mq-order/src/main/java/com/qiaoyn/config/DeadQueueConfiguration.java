package com.qiaoyn.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiaoyanan
 * date:2022/10/26 10:07
 */
@Configuration
public class DeadQueueConfiguration {

    // 1.声明创建Fanout路由模式的交换机——死信交换机
    @Bean
    public FanoutExchange deadExchange(){
        return new FanoutExchange("dead_fanout_exchange",true,false);
    }

    // 2.声明创建队列——死信队列
    @Bean
    public Queue deadQueue(){
        return new Queue("dead.fanout.queue",true);
    }

    // 3.绑定交换机与队列的关系，并设置交换机与队列之间的BindingKey
    @Bean
    public Binding deadBinding(){
        // 只有投递消息时指定的RoutingKey与这个BindingKey（dead）匹配上，消息才会被投递到该队列
        return BindingBuilder.bind(deadQueue()).to(deadExchange());
    }

}
