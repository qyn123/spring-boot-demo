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
 * 过期队列:https://blog.csdn.net/w15558056319/article/details/123495452
 * 对消息设置预期的时间，超过此时间后，消息被自动删除，消费者再无法接收获取
 * @author qiaoyanan
 * date:2022/10/25 17:45
 */
@Configuration
public class TtlQueueDirectConfiguration {

    // 1.声明创建direct路由模式的交换机
    @Bean
    public DirectExchange getDirectExchange(){
        /** @params1 : 交换机名称
         *  @params2 : 是否持久化（是，重启后不会消失）
         *  @params3 : 是否自动删除（交换机无消息可投递，则自动删除交换机）
         */
        return new DirectExchange("direct_ttl_exchange",true,false);
    }
    // 2.声明创建过期队列队列
    @Bean
    public Queue getTTLQueue1(){

        /** 2.1 设置该队列内的所有消息过期时间为5秒
         *      key：rabbitmq图形化界面中的规定名称
         *      value：默认是int类型值，否则报错，单位毫秒
         */
        Map<String,Object> map = new HashMap<>(1);
        map.put("x-message-ttl",5000);

        /** @params1 : 队列名称
         *  @params2 : 是否持久化（true：重启后不会消失）
         *  @params3 : 是否独占队列（true：仅限于此连接使用）
         *  @params4 : 是否自动删除（队列内最后一条消息被消费后，队列将自动删除）
         */
        return new Queue("ttl_Queue1",true,false,false,map);
    }

    //创建一个普通队列
    @Bean
    public Queue getTTLQueue2(){

        /** @params1 : 队列名称
         *  @params2 : 是否持久化（true：重启后不会消失）
         *  @params3 : 是否独占队列（true：仅限于此连接使用）
         *  @params4 : 是否自动删除（队列内最后一条消息被消费后，队列将自动删除）
         */
        return new Queue("ttl_message_Queue2",true,false,false);
    }


    // 3.绑定交换机与队列的关系，并设置交换机与队列之间的BindingKey
    @Bean
    public Binding getBindingTTL(){
        // 投递消息时指定的RoutingKey与此BindingKey（ttl）匹配上，消息才会被投递到ttl_Queue1队列
        return BindingBuilder.bind(getTTLQueue1()).to(getDirectExchange()).with("ttl");
    }

    // 3.绑定交换机与队列的关系，并设置交换机与队列之间的BindingKey
    @Bean
    public Binding getBindingTTLMessage(){
        // 投递消息时指定的RoutingKey与此BindingKey（ttl）匹配上，消息才会被投递到ttl_Queue1队列
        return BindingBuilder.bind(getTTLQueue2()).to(getDirectExchange()).with("message_ttl");
    }
}
