package com.qiaoyn.mapper;

import com.qiaoyn.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/09/29 14:50
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO `user`(`user_name`, `pass_word`, `role`) VALUES (#{userName}, #{passWord}, #{role});")
    void save(User user);

    @Select("select * from `user` where user_name = #{userName}")
    User findByUsername(String userName);

    @Select("select * from `user` ")
    List<User> findAll();
}
