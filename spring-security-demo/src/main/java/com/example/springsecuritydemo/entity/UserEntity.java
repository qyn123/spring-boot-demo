package com.example.springsecuritydemo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qiaoyanan
 * date:2022/10/08 11:02
 */
@Data
public class UserEntity implements Serializable {

    private Integer id;

    private String userName;

    private String passWord;

    private String role;
}
