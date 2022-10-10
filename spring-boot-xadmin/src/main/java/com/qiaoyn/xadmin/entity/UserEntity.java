package com.qiaoyn.xadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author qiaoyanan
 * date:2022/10/09 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserEntity extends DateEntity implements Serializable {

    private Integer id;

    private String userName;

    private String passWord;

    private String userNote;

    private Integer status;
}
