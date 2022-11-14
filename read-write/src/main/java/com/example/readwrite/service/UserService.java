package com.example.readwrite.service;

import com.example.readwrite.entity.User;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/11/14 15:51
 */
public interface UserService {

    /**
     * 查询所有用户
     */
    List<User> selectAllUsers();

    int addUser(User user);
}
