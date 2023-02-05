package com.qiaoyn.entity;

import lombok.Data;

/**
 * @author qiaoyanan
 * date:2023/02/04 16:29
 */
@Data
public class MessageOrder {

    private Integer id;
    private String context;
    private Integer orderId;
    private Integer status;
}
