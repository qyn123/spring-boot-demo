package com.qiaoyn.controller;

import com.qiaoyn.entity.User;
import com.qiaoyn.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/09/29 14:47
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserServiceInterface userServiceInterface;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    @Transactional
    public void save(@RequestBody User parameter) {
        User user = new User();
        user.setUserName(parameter.getUserName());
        user.setPassWord(bCryptPasswordEncoder.encode(parameter.getPassWord()));
        if("admin".equals(parameter.getUserName())){
            user.setRole("ADMIN");
        }else{
            user.setRole("USER");
        }
        userServiceInterface.save(user);
    }

    @GetMapping
    public User findByUsername(@RequestParam String username){
        return userServiceInterface.findByUsername(username);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")  //这一步很重要 拥有ADMIN权限的用户才能访问该请求
    public List<User> findAll(){
        return userServiceInterface.findAll();
    }

}

