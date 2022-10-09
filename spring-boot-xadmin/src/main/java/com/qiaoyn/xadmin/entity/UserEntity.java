package com.qiaoyn.xadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qiaoyanan
 * date:2022/10/09 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Integer id;

    private String userName;

    private String passWord;

    private String userNote;

    private Integer status;
}
