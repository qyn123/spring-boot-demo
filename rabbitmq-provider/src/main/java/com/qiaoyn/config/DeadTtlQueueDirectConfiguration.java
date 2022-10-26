package com.qiaoyn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiaoyanan
 * date:2022/10/26 10:09
 */
@Configuration
public class DeadTtlQueueDirectConfiguration {

    // 1.声明创建direct路由模式的交换机
    @Bean
    public DirectExchange getDirectTtlExchange(){
        return new DirectExchange("dead_direct_ttl_exchange",true,false);
    }

    // 2.声明创建过期队列队列
    @Bean
    public Queue getTtlQueueDead(){

        // 2.1 设置过期队列——该队列内的所有消息过期时间为5秒
        Map<String,Object> map = new HashMap<>(16);
        map.put("x-message-ttl",5000);
        // 2.2 设置消息接盘侠：消息过期后，不自动删除，而是将消息重新路由到dead_Exchange交换机
        map.put("x-dead-letter-exchange","dead_direct_exchange");
        // 2.3 设置消息接盘侠的具体路由key(// 如果是fanout模式的死信队列，则这里不需要设置投递的RoutingKey)
        map.put("x-dead-letter-routing-key","dead");

        return new Queue("ttl_Queue1_ttl",true,false,false,map);
    }

    // 3.绑定交换机与队列的关系，并设置交换机与队列之间的BindingKey
    @Bean
    public Binding getBindingQueueTtl(){
        // 只有投递消息时指定的RoutingKey与这个BindingKey（ttl）匹配上，消息才会被投递到ttl_Queue1队列
        return BindingBuilder.bind(getTtlQueueDead()).to(getDirectTtlExchange()).with("ttl");
    }

}
