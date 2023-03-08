package src.com.lc.demo.httpclient;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import src.com.lc.demo.utils.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 拉取乐橙生产产品数据
 * @author qiaoyanan
 */
public class Test {

	private final static String APPID = "000335";
	//private static String SURL = "http://api.dev.lechengyunfu.com/api/open_biz/index";
	private static String SURL = "https://api.51tsbx.com/api/open_biz/index";
	private static String SDK_VER = "1.0.0";

	public static void main(String[] args) {
		praseLcData("lc.order.product.list");
	}


	private static void praseLcData(String serviceName) {
		try {
			Map<String, String> postData = new HashMap<String, String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String timestamp = sdf.format(new Date());
			postData.put("appId", APPID);
			postData.put("serviceName", serviceName);
			// 这里的format是yyyyMMddHHmmssSSS
			postData.put("timestamp", "" + timestamp);
			postData.put("version", SDK_VER);
			postData.put("format", "Json");
			postData.put("charset", "UTF-8");

			String jsonContent = new Gson().toJson(DataUtil.getData());
			//基本信息加密
			String resCotent = new String(RSAEncrypt.encryptByPublicKey(jsonContent.getBytes("UTF-8"), RSAEncrypt.LC_PUBLIC_KEY));
			postData.put("bContent", resCotent);
			postData = MapUtil.sortMapByKey(postData);
			String sign = "";
			Iterator<Entry<String, String>> it = postData.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				sign += (entry.getKey() + "=" + entry.getValue() + "&");
			}
			sign = sign.substring(0, sign.length() - 1);
			//用商户自己私钥进行签名
			sign = RSASignature.sign(sign, RSAEncrypt.DEV_PRIVATE_KEY, "UTF-8");
			postData.put("sign", sign);
			postData.put("signType", "RSA");

			//发送请求组装数据
			RequestBuilder builder = RequestBuilder.post(SURL);
			Iterator<Entry<String, String>> it2 = postData.entrySet().iterator();
			while (it2.hasNext()) {
				Entry<String, String> entry = it2.next();
				builder.addParameter(entry.getKey(), entry.getValue());
			}
			HttpUriRequest post = builder.build();
			CloseableHttpResponse response = HttpClients.createDefault().execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				String body = EntityUtils.toString(resEntity, "UTF-8");
				// 这里可能返回错误的json {"errorCode":1000x,"errorMsg":"some msg"},需要单独判断一下
				Gson gson = new Gson();
				LcResp resp = gson.fromJson(body, LcResp.class);
				System.out.println(resp.toString());
				// 验证签名，使用HMB的公钥
				// 把返回中的sign和signType去除，然后用=号把值连接起来，接着用&符号拼接
				String unSign = "bContent=" + resp.getbContent() + "&charset=" + resp.getCharset() + "&format="
						+ resp.getFormat() + "&timestamp=" + resp.getTimestamp() + "&version=" + resp.getVersion();
				if (RSASignature.doCheck(unSign, resp.getSign(), RSAEncrypt.LC_PUBLIC_KEY)) {
					String decryptContent = RSAEncrypt.decryptByPrivateKey(resp.getbContent(),
							RSAEncrypt.DEV_PRIVATE_KEY);
					System.out.println("解密后的数据 ：" + unicodeToChina(decryptContent));
				} else {
					System.out.println("签名验证失败，数据错误");
				}
			} else {
				System.out.println("网络连接失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 汉字转换Unicode
	 * @param str
	 * @return
	 */
	public static String StringToUnicode(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				// uStr+="& "+Integer.toHexString(iValue)+";";
				uStr += "\\" + Integer.toHexString(iValue);
			} else {
				// uStr+="&#x"+Integer.toHexString(iValue)+";";
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}

	/**
	 * Unicode转换中文
	 * @param
	 * @return
	 */
	public static String UnicodeToString(String str) {
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(str, "\\u");
		while (st.hasMoreTokens()) {
			sb.append((char) Integer.parseInt(st.nextToken(), 16));
		}
		return sb.toString();
	}

	/**
	 * 中文转Unicode
	 * @param str
	 * @return
	 */
	public static String chineToUnicode(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				sb.append("\\u" + Integer.toHexString(c));
			}
		}
		return sb.toString();
	}

	/**
	 * Unicode转中文
	 * @param utfString
	 * @return
	 */
	public static String unicodeToChina(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}
		return sb.toString();
	}

}
