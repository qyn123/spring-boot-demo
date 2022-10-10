package com.qiaoyn.xadmin.service;

import com.qiaoyn.xadmin.entity.OrderEntityVo;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/10/10 16:34
 */
public interface OrderService {

    List<OrderEntityVo> queryOrderList();
}
