package com.qiaoyn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiaoyanan
 * date:2022/10/26 10:07
 */
@Configuration
public class MaxDeadQueueDirectConfiguration {

    // 1.声明创建direct路由模式的交换机——死信交换机
    @Bean
    public DirectExchange getMaxDead_DirectExchange(){
        return new DirectExchange("max_length_dead_direct_exchange",true,false);
    }

    // 2.声明创建队列——死信队列
    @Bean
    public Queue getMaxDead_Queue1(){
        return new Queue("max_length_dead_direct_Queue1",true);
    }

    // 3.绑定交换机与队列的关系，并设置交换机与队列之间的BindingKey
    @Bean
    public Binding getMaxBinding_TTL(){
        // 只有投递消息时指定的RoutingKey与这个BindingKey（dead）匹配上，消息才会被投递到该队列
        return BindingBuilder.bind(getMaxDead_Queue1()).to(getMaxDead_DirectExchange()).with("max.length.dead");
    }
}
