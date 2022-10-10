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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

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
                             @RequestParam("verifyCode") String verifyCode,
                             HttpSession session,
                             HttpServletRequest request,
                             Model model) {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
            //查询失败，返回提示信息给登录页面
            model.addAttribute("msg", "用户名或者密码不能为空!");
            return "/login";
        } else if (Objects.nonNull(request.getSession().getAttribute("verifyCode")) && !verifyCode.equalsIgnoreCase(String.valueOf(request.getSession().getAttribute("verifyCode")))) {
            log.info("用户:{},验证码:{}",username,request.getSession().getAttribute("verifyCode"));
            //判断验证码是否一致(忽略大小写)
            model.addAttribute("msg", "验证码不正确");
            return "/login";
        } else {
            UserEntity userEntity = userService.selectUserByNameAndPassWord(username, password);
            if (ObjectUtils.isEmpty(userEntity)) {
                //查询失败，返回提示信息给登录页面
                model.addAttribute("msg", "用户名或者密码错误!");
                return "/login";
            } else {
                log.info("用户{}登录成功,验证码:{}",username,request.getSession().getAttribute("verifyCode"));
                session.setAttribute("loginUser", username);
                //查询成功重定向到登录成功后进入index页面
                return "/index";
            }
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("userName") String userName,
                           @RequestParam("passWord") String passWord,
                           @RequestParam("userNote") String userNote, Model model) {
        if (ObjectUtils.isEmpty(userName) || ObjectUtils.isEmpty(passWord)) {
            model.addAttribute("msg", "注册时用户名或者密码不能为空!");
            return "/register";
        } else {
            int i = userService.selectUserByName(userName);
            if (i > 0) {
                model.addAttribute("msg", "用户已存在,请勿重复注册");
                return "register";
            } else {
                UserEntity userEntity = new UserEntity();
                userEntity.setUserName(userName).setPassWord(passWord).setUserNote(userNote);
                boolean b = userService.addUser(userEntity);
                if (b) {
                    return "/login";
                } else {
                    model.addAttribute("msg", "注册失败,请重新注册");
                    return "register";
                }
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
