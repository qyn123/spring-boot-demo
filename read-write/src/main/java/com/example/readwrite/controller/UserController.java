package com.example.readwrite.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.readwrite.entity.User;
import com.example.readwrite.entity.dto.UserDto;
import com.example.readwrite.service.UserService;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * @author qiaoyanan
 * date:2022/11/14 15:54
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/selectAll")
    public List<User> selectAll() {
        return userService.selectAllUsers();
    }

    @PostMapping
    public int addUser(@RequestBody UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setId(UUID.randomUUID().toString().replace("-", ""));
        user.setBirth(formatDate(userDto.getBirth()));
        return userService.addUser(user);
    }

    public Date formatDate(String dateStr) {
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new Date();
    }

}
