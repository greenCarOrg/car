package com.itee.bingsheng.pay.weixin.demo;


import com.itee.bingsheng.pay.base.utils.AmountUtils;
import com.itee.bingsheng.pay.base.utils.ParameterUtil;
import com.itee.bingsheng.pay.base.utils.http.HttpClient;
import com.itee.bingsheng.utils.MD5Util;
import com.itee.bingsheng.pay.base.utils.xml.XmlUtils;
import com.itee.bingsheng.pay.weixin.bean.CodeUrlReturnBean;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * @author ex_yangxiaoyi
 * 
 */
public class Demo {

	//微信支付商户开通后 微信会提供appid和appsecret和商户号partner
	private static String appId = "wxc0ad89d0e0da22c8";
	//private static String appid = "wxeedc80c157112b17";
	// 商户号
	private static String mchId = "1239851302";
	//private static String mch_id = "1230473202";

	// 应用密钥
	private static String partnerKey = "onKocla20160229xiachangweishezhi";
	//private static String key = "szidd20140808idingding1234567890";

	private static String partner = "";
	//这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	private static String partnerkey = "";
	//openId 是微信用户针对公众号的标识，授权的部分这里不解释
	private static String openId = "";
	//微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	private static String notifyurl = "";																	 // Key

	private static String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	private static String shorturl = "https://api.mch.weixin.qq.com/tools/shorturl";
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		getCodeurl();
	}
	

	/**
	 * 获取微信扫码支付二维码连接
	 */
	public static String getCodeurl() throws Exception {
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appId); 											// 公众号应用ID
		packageParams.put("mch_id", mchId); 										// 商户号
		packageParams.put("nonce_str", ParameterUtil.getRandomString(32)); 		// 随机数
		packageParams.put("body", "上飘");											// 商品描述根据情况修改
		packageParams.put("out_trade_no", ParameterUtil.getOrderId());				// 商户订单号
		packageParams.put("total_fee", AmountUtils.changeY2FByString("0.01"));   // 总金额
		packageParams.put("spbill_create_ip","127.0.0.1");						// 订单生成的机器 IP
		packageParams.put("notify_url", "http://183.62.138.52:8126/");			// 接收通知url
		packageParams.put("trade_type", "NATIVE");								// 支付类型
		packageParams.put("attach", "sdfsf");										// 附加数据

		// 为空参数不参与加密
		String signData = ParameterUtil.getSignData(packageParams);

		signData = signData +"&key=" +partnerKey;

		String sign =  MD5Util.ecodeByMD5(signData).toUpperCase();

		packageParams.put("sign", sign); // 签名 -签名生成算法

		String code_url = "";

		String xmlStr = XmlUtils.maptoXml(packageParams);
		String response = HttpClient.doPostForXml(reqUrl, xmlStr);

		// 可以开始解析xml文件
		CodeUrlReturnBean returnBean = XmlUtils.toObject(response, CodeUrlReturnBean.class);
		getShorturl(returnBean);
		code_url = returnBean.getCodeUrl();
		System.out.println(code_url);
		return code_url;
	}

	public static String  getShorturl(CodeUrlReturnBean returnBean) throws Exception {
		try {
			LinkedHashMap<String, String> packageParams = new LinkedHashMap<String, String>();
			packageParams.put("appid", appId); 											 // 公众账号ID
			packageParams.put("mch_id", mchId); 										 	 // 商户号
			packageParams.put("nonce_str", ParameterUtil.getRandomString(32)); 				 // 随机字符串

			StringBuffer long_url = new StringBuffer(returnBean.getCodeUrl());
			long_url.append("?sign=sign");
			long_url.append("&appid=").append(appId);
			long_url.append("&mch_id=").append(mchId);
			long_url.append("&mch_id=").append(mchId);
			long_url.append("&product_id=").append(returnBean.getOutTradeNo());
			long_url.append("&time_stamp=").append(new Date().getTime()+"");
			long_url.append("&nonce_str=").append(packageParams.get("nonce_str"));
			packageParams.put("long_url", ParameterUtil.urlEncodeUTF8(long_url.toString())); 	 // 需要转换的url

			String signData = ParameterUtil.getSignData(packageParams);							 // 将参数加密，为空参数不参与加密
			signData = signData +"&key=" + partnerKey;										 	 // 商户key
			packageParams.put("sign", MD5Util.ecodeByMD5(signData).toUpperCase());				 // 签名 -签名生成算法
			String xmlStr = XmlUtils.maptoXml(packageParams);                                    // 转换xml格式参数

			String response = HttpClient.doPostForXml(shorturl, xmlStr);				     // 发送请求
			System.out.println(response);

		}catch (Exception e){
			throw  new Exception(e);
		}
		return null;
	}
}
