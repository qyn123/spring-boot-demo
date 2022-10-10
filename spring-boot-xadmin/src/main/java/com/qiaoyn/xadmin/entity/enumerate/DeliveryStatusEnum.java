package com.qiaoyn.xadmin.entity.enumerate;

/**
 * 发货状态
 *
 * @author qiaoyanan
 * date:2022/10/10 14:50
 */
public enum DeliveryStatusEnum {
    //发货状态(0:未发货,1:已发货,2:已取消)
    NO_DELIVERY(0, "未发货"),
    HAS_DELIVERY(1, "已发货"),
    HAS_CANCEL(2, "已取消"),
    ;
    private Integer code;
    private String name;

    DeliveryStatusEnum(Integer code, String name) {
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

    public static String getName(Integer code) {
        DeliveryStatusEnum[] carTypeEnums = values();
        for (DeliveryStatusEnum enumTest : carTypeEnums) {
            if (enumTest.getCode().equals(code)) {
                return enumTest.getName();
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        DeliveryStatusEnum[] carTypeEnums = values();
        for (DeliveryStatusEnum enumTest : carTypeEnums) {
            if (enumTest.getName().equals(name)) {
                return enumTest.getCode();
            }
        }
        return null;
    }
}
