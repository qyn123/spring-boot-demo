package com.qiaoyn.xadmin.entity.enumerate;

/**
 * 支付状态
 * @author qiaoyanan
 * date:2022/10/10 14:44
 */
public enum PayStatusEnum {
    //支付状态(0:已支付,1:未支付)
    HAS_PAY(0,"已支付"),
    NO_PAY(1,"未支付")
    ;
    private Integer code;
    private String name;

    PayStatusEnum(Integer code, String name) {
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
        PayStatusEnum[] carTypeEnums = values();
        for (PayStatusEnum enumTest : carTypeEnums) {
            if (enumTest.getCode().equals(code)) {
                return enumTest.getName();
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        PayStatusEnum[] carTypeEnums = values();
        for (PayStatusEnum enumTest : carTypeEnums) {
            if (enumTest.getName().equals(name)) {
                return enumTest.getCode();
            }
        }
        return null;
    }
}
