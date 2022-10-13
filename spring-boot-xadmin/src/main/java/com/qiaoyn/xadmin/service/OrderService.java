package com.qiaoyn.xadmin.service;

import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.OrderEntity;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.dto.OrderQuery;

/**
 * @author qiaoyanan
 * date:2022/10/10 16:34
 */
public interface OrderService {

    PageInfo<OrderEntityVo> queryOrderList(OrderQuery orderQuery);

    boolean deleteUserById(String id);

    boolean addOrder(OrderEntity orderEntity);
}
