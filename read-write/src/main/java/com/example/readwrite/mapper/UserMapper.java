package com.example.readwrite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.readwrite.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qiaoyanan
 * date:2022/11/14 15:50
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
