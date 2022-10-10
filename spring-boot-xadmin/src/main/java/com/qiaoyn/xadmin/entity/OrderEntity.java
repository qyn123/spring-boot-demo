package com.qiaoyn.xadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单管理
 *
 * @author qiaoyanan
 * date:2022/10/10 16:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderEntity extends DateEntity implements Serializable {

    private Integer orderId;
    private String userName;
    private Double totalMoney;
    private Double dealMoney;
    private Integer orderStatus;
    private Integer payStatus;
    private Integer payMethod;
    private Integer deliveryStatus;
    private Integer sendMethod;


}
