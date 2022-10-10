package com.qiaoyn.xadmin.controller;

import com.qiaoyn.xadmin.entity.UserEntity;
import com.qiaoyn.xadmin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qiaoyanan
 * date:2022/10/08 15:58
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String selectUser(@RequestParam("userName") String username,
                             @RequestParam("passWord") String password,
                             Model model) {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
            //查询失败，返回提示信息给登录页面
            model.addAttribute("msg", "用户名或者密码不能为空!");
            return "/login";
        } else {
            UserEntity userEntity = userService.selectUserByNameAndPassWord(username, password);
            if (ObjectUtils.isEmpty(userEntity)) {
                //查询失败，返回提示信息给登录页面
                model.addAttribute("msg", "用户名或者密码错误!");
                return "/login";
            } else {
                //查询成功重定向到登录成功后进入index页面
                return "/index";
            }
        }
    }


    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
