package com.qiaoyn.dao;

import com.qiaoyn.entity.MessageOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2023/02/04 16:33
 */
@Mapper
public interface MessageOrderDao {

    @Select("select * from message_order where status = #{status}")
    List<MessageOrder> queryListByStatus(@Param("status") Integer status);
}
