package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.UserEntity;
import com.example.springsecuritydemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author qiaoyanan
 * date:2022/10/08 10:42
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.selectByUserName(username);
        if (ObjectUtils.isEmpty(userEntity)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String cryptPwd = passwordEncoder.encode(userEntity.getPassWord());
        //校验账号的密码和权限
        User userDetails = new User(userEntity.getUserName(), cryptPwd, AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getRole()));
        return userDetails;
    }
}
