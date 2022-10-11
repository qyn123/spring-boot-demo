package com.qiaoyn.xadmin.mapper;

import com.qiaoyn.xadmin.entity.OrderEntityVo;
import com.qiaoyn.xadmin.entity.dto.OrderQuery;
import org.apache.ibatis.annotations.Param;

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
    List<OrderEntityVo> queryOrderList(OrderQuery orderQuery);

    int deleteUserById(@Param("id") Integer id);
}
