package com.example.demo;

import cn.hutool.extra.qrcode.QrCodeUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * @author qiaoyanan
 * date:2022/08/04 9:20
 */
public class Test11 {
    public static void main(String[] args) {

    }

    public void getQrCode(HttpServletResponse response) throws Exception {
        QrCodeUtil.generate("https://login.health.lejian.com/login?_site=dsa&newCompanyId=4310343", 300, 300, "jpg", response.getOutputStream());
    }
}
