package com.qiaoyn.service.impl;

import com.qiaoyn.entity.JwtUser;
import com.qiaoyn.entity.User;
import com.qiaoyn.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

/**
 * @author qiaoyanan
 * date:2022/09/29 15:11
 */
@Service
public class JwtUserService implements UserDetailsService {

    @Autowired
    UserServiceInterface userService;

    /**
     * 根据前端传入的用户信息 去数据库查询是否存在该用户
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userService.findByUsername(s);
        if (user != null) {
            JwtUser jwtUser = new JwtUser(user);
            return jwtUser;
        } else {
            try {
                throw new ValidationException("该用户不存在");
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
