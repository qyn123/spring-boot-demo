package com.qiaoyn.xadmin.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qiaoyanan
 * date:2022/10/09 13:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {
    // 当前的页码
    private Integer pageNum = 1;
    //每一页所显示的数量
    private Integer pageSize  = 5;
    // 根据用户查询
    private String userName;
}

