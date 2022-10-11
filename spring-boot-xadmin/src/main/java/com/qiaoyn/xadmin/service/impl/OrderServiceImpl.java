package com.qiaoyn.xadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.dto.OrderQuery;
import com.qiaoyn.xadmin.mapper.OrderMapper;
import com.qiaoyn.xadmin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new PageInfo<>(orderMapper.queryOrderList(orderQuery));
    }

    @Override
    public boolean deleteUserById(Integer id) {
        int i = orderMapper.deleteUserById(id);
        if (i > 0) {
            return true;
        }
        return false;
    }
}
