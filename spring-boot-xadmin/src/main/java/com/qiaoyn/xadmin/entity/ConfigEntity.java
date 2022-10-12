package com.qiaoyn.xadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author qiaoyanan
 * date:2022/10/12 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ConfigEntity extends DateEntity implements Serializable {

    private Integer id;
    private String groupName;
    private Integer keyName;
    private String valueName;
    private Integer status;
}
