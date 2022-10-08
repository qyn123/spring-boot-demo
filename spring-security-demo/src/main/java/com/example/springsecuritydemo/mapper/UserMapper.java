package com.example.springsecuritydemo.mapper;

import com.example.springsecuritydemo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author qiaoyanan
 * date:2022/10/08 11:01
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where user_name = #{userName}")
    UserEntity selectByUserName(String userName);
}
