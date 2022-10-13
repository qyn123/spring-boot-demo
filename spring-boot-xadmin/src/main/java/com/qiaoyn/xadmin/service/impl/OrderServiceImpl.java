package com.qiaoyn.xadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.OrderEntity;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.dto.OrderQuery;
import com.qiaoyn.xadmin.mapper.OrderMapper;
import com.qiaoyn.xadmin.service.OrderService;
import com.qiaoyn.xadmin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

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
    public PageInfo<OrderEntityVo> queryOrderList(OrderQuery orderQuery) {
        PageHelper.startPage(orderQuery.getPageNum(),orderQuery.getPageSize());
        if (StringUtils.isNotBlank(orderQuery.getBeginDate())) {
            orderQuery.setBeginDate(DateUtil.getStartTime(orderQuery.getBeginDate()));
        }
        if (StringUtils.isNotBlank(orderQuery.getEndDate())) {
            orderQuery.setEndDate(DateUtil.getEndTime(orderQuery.getEndDate()));
        }
        System.out.println("size================>" +orderMapper.queryOrderList(orderQuery).size());
        return new PageInfo<>(orderMapper.queryOrderList(orderQuery));
    }

    @Override
    public boolean deleteUserById(String id) {
        int i = orderMapper.deleteUserById(id);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean addOrder(OrderEntity orderEntity) {
        orderEntity.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
        return orderMapper.addOrder(orderEntity) > 0;
    }

    @Override
    public OrderEntityVo queryOrderById(String id) {
        return orderMapper.queryOrderById(id);
    }

    @Override
    @Transactional
    public boolean updateOrder(OrderEntity orderEntity) {
        return orderMapper.updateOrder(orderEntity) > 0;
    }
}
