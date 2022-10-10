package com.qiaoyn.xadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author qiaoyanan
 * date:2022/10/10 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateEntity implements Serializable {

    private String createTime;
    private String updateTime;
}
