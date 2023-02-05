package com.qiaoyn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author qiaoyanan
 * date:2022/11/04 14:23
 */
// 也可以不用编写以下配置类，直接在图形化界面创建好队列和交换机也可以
@Configuration
public class OrderRabbitConfiguration {

    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("order_confirm_fanoutExchange",true,false);
    }

    @Bean
    public Queue getQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange","dead_fanout_exchange");
        return new Queue("order_confirm_fanoutQueue",true,false,false, args);
    }

    @Bean
    public Binding getBinding(){
        return BindingBuilder.bind(getQueue()).to(getExchange());
    }
}
