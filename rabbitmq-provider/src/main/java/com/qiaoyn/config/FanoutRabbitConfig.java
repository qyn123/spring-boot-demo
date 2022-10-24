package com.qiaoyn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 广播模式交换机
 * @author qiaoyanan
 * date:2022/10/24 13:57
 */
@Configuration
public class FanoutRabbitConfig {

    @Bean
    public FanoutExchange getFanoutExchange() {
        /**
         * @params1 ：交换机名称
         * @params2 ：是否持久化
         * @params4 ：是否自动删除
         */
        return new FanoutExchange("fanout_order_exchange",true,false);
    }

    // 2.声明三个队列队列：emailQueue、smsQueue、qqQueue
    @Bean
    public Queue getEmailQueue(){

        /**
         * @params1 ：队列名称
         * @params2 ：队列是否持久化（如果是，则重启服务不会丢失）
         * @params3 ：是否是独占队列（如果是，则仅限于此连接）
         * @params4 ：是否自动删除（最后一条消息消费完毕，队列是否自动删除）
         */
        return new Queue("email_fanout_Queue",true,false,false);
    }
    @Bean
    public Queue getSMSQueue(){
        return new Queue("sms_fanout_Queue",true,false,false);
    }
    @Bean
    public Queue getQqQueue(){
        return new Queue("qq_fanout_Queue",true,false,false);
    }

    // 3.绑定交换机与队列的关系
    @Bean
    public Binding getEmailBinding(){
        return BindingBuilder.bind(getEmailQueue()).to(getFanoutExchange());
    }
    @Bean
    public Binding getSMSBinding(){
        return BindingBuilder.bind(getSMSQueue()).to(getFanoutExchange());
    }
    @Bean
    public Binding getQQBinding(){
        return BindingBuilder.bind(getQqQueue()).to(getFanoutExchange());
    }


}
