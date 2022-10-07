package com.qiaoyn.upload.config;

import com.qiaoyn.upload.constant.SimpleMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiaoyanan
 * date:2022/08/15 10:40
 */
@Configuration
public class RabbitMqTemplateConfig {



    /**
     * 声明Exchange
     *
     * @return
     */
    @Bean(name = "directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(SimpleMqConstant.DIRECT_EXCHANGE_NAME);
    }

    /**
     * 声明队列
     *
     * @return
     */
    @Bean(name = "directQueue")
    public Queue directQueue() {
        return QueueBuilder.durable(SimpleMqConstant.DIRECT_QUEUE_NAME).build();
    }

    /**
     * 声明确认队列绑定关系
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding queueBinding(@Qualifier("directQueue") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SimpleMqConstant.ROUTING_KEY);
    }
}

