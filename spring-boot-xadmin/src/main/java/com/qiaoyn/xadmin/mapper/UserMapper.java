package com.qiaoyn.xadmin.mapper;

import com.qiaoyn.xadmin.entity.UserEntity;
import com.qiaoyn.xadmin.entity.dto.UserQuery;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/10/09 13:48
 */
public interface UserMapper {

    // 查询所有用户
    public List<UserEntity> listUser();

    // 根据id查询用户
    public UserEntity queryUserById(Integer id);

    // 根据用户名来查询用户  并分页展示
    public List<UserEntity> listUserByName(UserQuery userQuery);

    //根据id删除用户
    public int deleteUserById(Integer id);

    // 修改用户
    public int updateUser(UserEntity user);

    // 新增用户
    public int addUser(UserEntity user);

    // 判断用户名是否存在
    public int selectUserByName(String name);

}
