package com.qiaoyn.service;

import com.qiaoyn.entity.User;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/09/29 14:48
 */
public interface UserServiceInterface {

    void save(User user);

    User findByUsername(String username);

    List<User> findAll();
}
