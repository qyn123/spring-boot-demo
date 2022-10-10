package com.qiaoyn.xadmin.entity.enumerate;

/**
 * @author qiaoyanan
 * date:2022/10/10 14:54
 */
public enum SendMethodEnum {
    //配送方式(0:京东物流,1:顺丰物流,2:邮政,3:其他)
    JD(0,"京东物流"),
    SF(1,"顺丰物流"),
    YZ(2,"邮政"),
    OTHER(3,"其他"),
    ;
    private Integer code;
    private String name;

    SendMethodEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
