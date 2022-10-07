package com.qiaoyn.upload.controller;

import com.qiaoyn.upload.constant.SimpleMqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author qiaoyanan
 * date:2022/08/15 13:50
 */
@Component
public class DirectConsumer {

    private static Logger logger = LoggerFactory.getLogger(DirectConsumer.class);

    @RabbitListener(queues = SimpleMqConstant.DIRECT_QUEUE_NAME)
    public void receiveMsg(Message message) {
        String msg = new String(message.getBody());
        logger.info("接收到队列direct.queue消息：{}", msg);
        
    }
}

