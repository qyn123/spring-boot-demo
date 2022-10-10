package com.qiaoyn.xadmin.entity.enumerate;

/**
 * 支付方式
 *
 * @author qiaoyanan
 * date:2022/10/10 14:46
 */
public enum PayMethodEnum {
    // 支付方式(0:支付宝,1:微信,2:货到付款)
    A_LI_PAY(0, "支付宝"),
    WE_CHAT_PAY(1, "微信"),
    CASH_ON_DELIVERY(2, "货到付款");
    private Integer code;
    private String name;

    PayMethodEnum(Integer code, String name) {
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
        PayMethodEnum[] carTypeEnums = values();
        for (PayMethodEnum enumTest : carTypeEnums) {
            if (enumTest.getCode().equals(code)) {
                return enumTest.getName();
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        PayMethodEnum[] carTypeEnums = values();
        for (PayMethodEnum enumTest : carTypeEnums) {
            if (enumTest.getName().equals(name)) {
                return enumTest.getCode();
            }
        }
        return null;
    }
}
