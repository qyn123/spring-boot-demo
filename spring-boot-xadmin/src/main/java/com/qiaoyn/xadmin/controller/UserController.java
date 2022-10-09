package com.qiaoyn.xadmin.controller;

import com.github.pagehelper.PageInfo;
import com.qiaoyn.xadmin.entity.UserEntity;
import com.qiaoyn.xadmin.entity.dto.UserQuery;
import com.qiaoyn.xadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author qiaoyanan
 * date:2022/10/09 14:04
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model, UserQuery userQuery) {
        PageInfo<UserEntity> userPageInfo = userService.listUserByName(userQuery);
        model.addAttribute("page", userPageInfo);
        return "/user/show-user";
    }


    @PostMapping("/")
    public String listUserByName(Model model, UserQuery userQuery) {
        PageInfo<UserEntity> userPageInfo = userService.listUserByName(userQuery);
        model.addAttribute("page", userPageInfo);
        return "/user/show-user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        boolean b = userService.deleteUserById(id);
        if (b) {
            attributes.addFlashAttribute("message", "删除用户成功");
            return "redirect:/user/";
        } else {
            attributes.addFlashAttribute("message", "删除用户失败");
            return "redirect:/user/";
        }
    }


    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.queryUserById(id));
        return "/user/editUser";
    }


    @PostMapping("/edit")
    public String edit(UserEntity user, RedirectAttributes attributes) {
        UserEntity userEntity = userService.queryUserById(user.getId());
        //用户不存在
        if (ObjectUtils.isEmpty(userEntity)) {
            boolean b = userService.addUser(user);
            if (b) {
                attributes.addFlashAttribute("message", " 新增用户成功");
                return "redirect:/user/";
            } else {
                attributes.addFlashAttribute("message", "新增用户失败");
                return "redirect:/user/";
            }
        } else {
            boolean b = userService.updateUser(user);
            if (b) {
                attributes.addFlashAttribute("message", " 更新用户成功");
                return "redirect:/user/";
            } else {
                attributes.addFlashAttribute("message", "更新用户失败");
                return "redirect:/user/";
            }
        }
    }

    /* 新增操作 */
    @GetMapping("/update")
    public String toUpdate(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        return "/user/editUser";
    }
}
