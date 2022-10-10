package com.qiaoyn.xadmin.service;

import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.UserEntity;
import com.qiaoyn.xadmin.entity.dto.UserQuery;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/10/09 14:01
 */
public interface UserService {

    // 查询所有用户
    public List<UserEntity> listUser();

    // 根据用户名来查询用户  并分页展示
    public PageInfo<UserEntity> listUserByName(UserQuery userQuery);

    //根据id删除用户
    public boolean deleteUserById(Integer id);

    // 根据id查询用户
    public UserEntity queryUserById(Integer id);

    // 修改用户
    public boolean updateUser(UserEntity user);

    // 新增用户
    public boolean addUser(UserEntity user);

    public int selectUserByName(String name);

    public UserEntity selectUserByNameAndPassWord(String name,String passWord);
}
