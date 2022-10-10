package com.qiaoyn.xadmin.service.impl;

import com.qiaoyn.xadmin.entity.OrderEntity;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.enumerate.*;
import com.qiaoyn.xadmin.mapper.OrderMapper;
import com.qiaoyn.xadmin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author qiaoyanan
 * date:2022/10/10 16:35
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<OrderEntityVo> queryOrderList() {
        List<OrderEntityVo> list = new ArrayList<>();
        List<OrderEntityVo> orderEntityList = orderMapper.queryOrderList();
        orderEntityList.forEach(orderEntityVo -> {
            OrderEntityVo vo  = new OrderEntityVo();
            BeanUtils.copyProperties(orderEntityVo,vo);
            vo.setOrderStatusName(OrderStatusEnum.getName(orderEntityVo.getOrderStatus()));
            vo.setDeliveryStatusName(DeliveryStatusEnum.getName(orderEntityVo.getDeliveryStatus()));
            vo.setPayMethodName(PayMethodEnum.getName(orderEntityVo.getPayMethod()));
            vo.setSendMethodName(SendMethodEnum.getName(orderEntityVo.getSendMethod()));
            vo.setPayStatusName(PayStatusEnum.getName(orderEntityVo.getPayStatus()));
            list.add(vo);
        });
        return list;
    }
}
