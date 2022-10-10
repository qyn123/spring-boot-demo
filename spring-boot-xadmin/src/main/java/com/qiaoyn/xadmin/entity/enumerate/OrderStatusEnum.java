package com.qiaoyn.xadmin.entity.enumerate;

/**
 * 订单状态
 * @author qiaoyanan
 * date:2022/10/10 14:35
 */
public enum OrderStatusEnum {
    //0:待确认,1:已确认,2:已收货,3:已取消,4:已完成,5:已作废
    TO_BE_CONFIRMED(0,"待确认"),
    CONFIRMED(1,"已确认"),
    GOODS_RECEIVED(2,"已收货"),
    CANCELLED(3,"已取消"),
    DONE(4,"已完成"),
    VOIDED(5,"已作废")
    ;

    private Integer code;
    private String name;

    OrderStatusEnum(Integer code, String name) {
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
