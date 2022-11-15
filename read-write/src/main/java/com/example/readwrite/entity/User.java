package com.example.readwrite.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiaoyanan
 * date:2022/11/14 15:49
 */
@Data
public class User implements Serializable {
     private String id;
     private String name;
     private Date birth;
}
