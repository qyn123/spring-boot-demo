package com.qiaoyn.service.impl;

import com.qiaoyn.entity.User;
import com.qiaoyn.mapper.UserMapper;
import com.qiaoyn.service.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/09/29 14:49
 */
@Service
public class UserServiceInterfaceImpl implements UserServiceInterface {

    @Resource
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
