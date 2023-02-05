package com.qiaoyn.entity;

import lombok.Data;

/**
 * @author qiaoyanan
 * date:2022/11/04 11:45
 */
@Data
public class Order {

    // 订单ID
    private int orderId;

    // 用户名
    private String userName;

    // 商品内容
    private String context;

    // 购买数量
    private int num;
}
