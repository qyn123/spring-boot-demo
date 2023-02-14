package src.com.lc.demo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncrypt {
 
	/**
	 * 开发者私钥 (注意：JAVA使用的是PKCS8的编码，如果私钥不是PKCK8的编码，请使用Openssl进行PKCS8编码)
	 * */
	  public final static String DEV_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKgStFHXyVP2y7cWIWtZ3utcxnN1MAnNVyXp7uX2KaJtX1lwCz0N+sVq+Jo5ySsoxDUXb3lnVS4eVscjcNrbgO/4RhYW5NIz3aQqw86mlBbpN6H7YXEE+x2DqMQMasFjqPc7HisUvil4k0XhSQJWW4DJQ6dr1hiajM7C520DMdonAgMBAAECgYAYI9+cZbb4BVmLKXWRWq4m9gNW7/z/W2vp+SikBPBD8Ruh1/L1SP28vzmHxUb8mPVdfsk3Eb1tS1RBc/iwpphlI5a6MgVn7iRPFH9skhVjrn7HNU8Nn88ow8Aa+wZD+1zq4BwQx56uq85DlqnKlm0jYjiz6uAn9LJ970X6+8JUAQJBANLAG4jMWfW5/q6fXaGeY1ynycDOFnEh0uKNad/qnKTosqQhNm+dNG6EQMwl1Tb2UOQ8I+n266pK7Rcgz0KoFgECQQDMKNY6O43uOsjYdeSXJESeMyrSbvCK2kg9DgGQd8tHanTqyCueGXhigNOBZWSLBNzMvZcLun385Doew8DsloAnAkEAsGCWqqZbo4tF2l8FvX0RKzpBX1sa7ROhqnkvYna3IkydNT+Wj1LBzrxgfnorNxRuHpU2eVhtYtfSFMkgrGiAAQJBAJZWrAqNuGJpn2Tmy8IQzwbAHxR+5Vsain+LRNOho2uflSEe7h+qGeBPhXCPvY4xsnaCViwo1IXZeoMVG4gc+5kCQDlFlHI1TVl4/oZYkcb66ZPFyCqywhuEq1e8zMrKi002TDTDTGbN/B0njj9vJy62+aUck9BLm/dfjHnRlYt+0+I=";

	/**
	 * 公钥
	 **/
	  public final static String LC_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+erO9Zc9Vn4L/aojHKT04GhkSSkM27toXs/y+OnvpzR80G0XycI2E0rN3EpGONAeiadr78qmZ3fxZGGCtXhlkjIr0OBinLLRwPL+JYUUiAwybVl1WXKmm5133REtD9PAom5ei7LMoGunXJlyhl8zQ6OnsNm26rfHTN03V0NNf2QIDAQAB";
	
	    
	  
	  
	  /**
	   * RSA加密方法
	   * @param data 待加密数据
	   * @param publicKey 公钥字符串
	   * @return
	   * @throws Exception
	   */
	    public static String encryptByPublicKey(byte[] data, String publicKey)  
	            throws Exception {  
	        byte[] keyBytes = Base64.decode(publicKey);  
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key publicK = keyFactory.generatePublic(x509KeySpec);  
	        // 对数据加密  
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
	        int inputLen = data.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        // 对数据分段加密  
	        while (inputLen - offSet > 0) {  
	            if (inputLen - offSet > 117) {  
	                cache = cipher.doFinal(data, offSet, 117);  
	            } else {  
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
	            }  
	            out.write(cache, 0, cache.length);  
	            i++;  
	            offSet = i * 117;  
	        }  
	        byte[] encryptedData = out.toByteArray();  
	        out.close();  
	        return Base64.encode(encryptedData);
	    }  
	    
	    /**
		   * RSA解密方法
		   * @param data 待解密数据
		   * @param publicKey 私钥字符串
		   * @return
		   * @throws Exception
		   */
	    public static String decryptByPrivateKey(String encryptedString, String privateKey)  
	            throws Exception {  
	    	byte[] encryptedData = Base64.decode(encryptedString);
 	        byte[] keyBytes = Base64.decode(privateKey);  
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
	        cipher.init(Cipher.DECRYPT_MODE, privateK);  
	        int inputLen = encryptedData.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        // 对数据分段解密  
	        while (inputLen - offSet > 0) {  
	            if (inputLen - offSet > 128) {  
	                cache = cipher.doFinal(encryptedData, offSet, 128);  
	            } else {  
	                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
	            }  
	            out.write(cache, 0, cache.length);  
	            i++;  
	            offSet = i * 128;  
	        }  
	        byte[] decryptedData = out.toByteArray();  
	        out.close();  
	        return new String(decryptedData,"UTF-8");  
	    } 

}
