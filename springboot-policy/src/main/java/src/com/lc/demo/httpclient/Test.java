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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



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
					System.out.println("解密后的数据 ：" + decryptContent);
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
}
