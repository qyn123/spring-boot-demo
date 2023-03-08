package src.com.lc.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {
	
	
	/**
	 * 基本数据
	 */
	public static Map<String, String> getData(){
		Map<String, String> bzContent = new HashMap<String, String>();
		bzContent.put("product_code", "1004001");
		bzContent.put("biz_company_code", "shts");
		//订单号/流水号 唯一值
		bzContent.put("biz_order_sn", ""+System.currentTimeMillis());
		bzContent.put("policy_user_name", "李先生");
		bzContent.put("policy_user_certtype", "1");
		bzContent.put("policy_user_certno", "42112419860716301X");
		bzContent.put("policy_user_mobile", "13678951256");
		bzContent.put("policy_user_email", "test@test.com");
		bzContent.put("insured_user_name", "李先生");
		bzContent.put("insured_user_certtype", "1");
		bzContent.put("insured_user_certno", "42112419860716301X");
		bzContent.put("insured_user_mobile", "13578954256");
		bzContent.put("benefited_rel", "1");
		bzContent.put("benefited_user_name", "");
		bzContent.put("benefited_user_certtype", "");
		bzContent.put("benefited_user_certno", "");
		bzContent.put("benefited_user_mobile", "");
		bzContent.put("policy_start_time", "2015-11-03 00:00:00");
		bzContent.put("policy_end_time", "2015-11-03 23:59:59");
		bzContent.put("page", "2");
		bzContent.put("perPage","1000");

		return bzContent;
	}
	
}
