package com.qiaoyn.task;

import com.qiaoyn.dao.MessageOrderDao;
import com.qiaoyn.dao.OrdersDao;
import com.qiaoyn.entity.MessageOrder;
import com.qiaoyn.entity.Order;
import com.qiaoyn.service.MqService;
import com.qiaoyn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2023/02/04 16:19
 */
@Component
public class MqSendTask {

    @Autowired
    private MqService mqService;
    @Autowired
    private MessageOrderDao messageOrderDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private OrderService orderService;

    @Scheduled(cron="0/5 * *  * * ? ")
    //每5秒执行一次
    public void  test () {
        //查询订单信息表中状态为0的订单分发到消息队列中(因为订单为0没发送到消息队列中去)
        List<MessageOrder> daoList = messageOrderDao.queryListByStatus(0);
        if (daoList.size() > 0) {
            for (MessageOrder messageOrder : daoList) {
                Order order = ordersDao.selectOrderById(messageOrder.getOrderId());
                // 2.通过mq分发消息
                mqService.pushMessage(order);
            }
        }
    }
}
