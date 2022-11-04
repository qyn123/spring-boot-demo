package com.qiaoyn.dao;

import com.qiaoyn.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author qiaoyanan
 * date:2022/11/04 11:49
 */
@Service
public class OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 创建订单
    public void saveOrder(Order order) throws Exception {
        String addSql = "insert into orders (id,username,context,num) values (?,?,?,?)";
        int count = jdbcTemplate.update(addSql, order.getOrderId(),order.getUserName(), order.getContext(), order.getNum());
        if(count!=1){
            throw new Exception("订单创建失败，原因【数据库插入失败】");
        }
    }


    // 可靠生产：创建订单
    public void saveMqOrder(Order order) throws Exception {
        // 1.保存订单到数据库
        String addSql = "insert into orders (id,username,context,num) values (?,?,?,?)";
        int count = jdbcTemplate.update(addSql, order.getOrderId(),order.getUserName(), order.getContext(), order.getNum());
        if(count!=1){
            throw new Exception("订单创建失败，原因【数据库插入失败】");
        }
        /**
         2.冗余一份订单信息到本地数据库
         原因：下单之后，rabbit可能会出现宕机，就引发消息没有投递成功，为了消息可靠生产，对消息做一次冗余
         */
        saveLocalMessage(order);
    }

    /**
     * 保存消息到本地数据库
     */
    public void saveLocalMessage(Order order) throws Exception {
        String addSql = "insert into message_order (context,orderId,status) values (?,?,?)";
        int count =jdbcTemplate.update(addSql, order.getContext(), order.getOrderId(),"0");
        if(count!=1){
            throw new Exception("冗余订单信息保存失败，原因【数据库插入失败】");
        }
    }
}
