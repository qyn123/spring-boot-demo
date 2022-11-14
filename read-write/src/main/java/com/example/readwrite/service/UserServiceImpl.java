package com.example.readwrite.service;

import com.example.readwrite.entity.User;
import com.example.readwrite.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/11/14 15:53
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }
}
