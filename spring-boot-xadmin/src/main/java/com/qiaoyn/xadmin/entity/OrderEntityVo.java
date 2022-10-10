package com.qiaoyn.xadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author qiaoyanan
 * date:2022/10/10 17:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderEntityVo extends OrderEntity implements Serializable {

    private String orderStatusName;
    private String payStatusName;
    private String payMethodName;
    private String deliveryStatusName;
    private String sendMethodName;
}
