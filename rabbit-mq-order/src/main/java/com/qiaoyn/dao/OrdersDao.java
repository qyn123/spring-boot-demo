package com.qiaoyn.dao;

import com.qiaoyn.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author qiaoyanan
 * date:2023/02/04 16:38
 */
@Mapper
public interface OrdersDao {

    @Select("select * from orders where id = #{orderId} ")
    Order selectOrderById(Integer orderId);
}
