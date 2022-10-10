package com.qiaoyn.xadmin.mapper;

import com.qiaoyn.xadmin.entity.OrderEntityVo;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/10/10 16:30
 */
public interface OrderMapper {

    /**
     * 查询所有订单信息
     * @return list
     */
    List<OrderEntityVo> queryOrderList();
}
