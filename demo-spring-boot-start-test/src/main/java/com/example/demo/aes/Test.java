package com.example.demo.aes;

public class Test {


    /**
     * 示例
     *   H1CLneuLxGK8j1FT  为测试秘钥
     *   jsonStr           传输报文
     * */

    public static void main(String[] args) {
        String jsonStr = "{'selfName': '本人姓名','selfIdNo': '370101198001010094','relation': '01','name': '体检人姓名','idType': '01','idNo': '370101198001010131','mobile': '13212121212','sex': '0','amount': '1','maritalStatus': '0','birthday': '1980-12-12'}";
        String encodeStr = AESEncrypt.encode(jsonStr, "H1CLneuLxGK8j1FT");
        System.out.println(encodeStr);
        String decodeStr = AESEncrypt.decode(encodeStr, "H1CLneuLxGK8j1FT");
        System.out.println(decodeStr);

    }
}