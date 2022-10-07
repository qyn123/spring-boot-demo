package com.example.demo.aes;

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @Date: 2022-07-04 11:48
 * @Remark: 外部数据加密处理
 * ECB模式
 */
public class AESEncrypt {

    /**
     * 渠道加密方式
     *
     * @param value
     * @param key
     * @return
     */
    public static String encode(String value, String key) {
        try {
            if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
                return null;
            }
            key = hash(key).substring(8, 24);
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/" + "ECB" + "/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encrypted = cipher.doFinal(value.getBytes("utf-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 渠道解密方式
     *
     * @param value
     * @param key
     * @return
     */
    public static String decode(String value, String key) {
        try {
            if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
                return null;
            }
            key = hash(key).substring(8, 24);
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/" + "ECB" + "/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            //先用base64解密
            byte[] encrypted1 = Base64.getDecoder().decode(value);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**md5 部分 */
    private static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            return s;
        }
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
        }
        return null;
    }
}