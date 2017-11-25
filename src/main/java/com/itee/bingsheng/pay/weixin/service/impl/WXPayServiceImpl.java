package com.itee.bingsheng.pay.weixin.service.impl;

import com.alipay.api.internal.util.StringUtils;
import com.itee.bingsheng.entity.Order;
import com.itee.bingsheng.pay.base.bean.CodeUrlBean;
import com.itee.bingsheng.pay.base.bean.OrderBean;
import com.itee.bingsheng.pay.base.enums.ReturnCode;
import com.itee.bingsheng.pay.base.utils.AmountUtils;
import com.itee.bingsheng.pay.base.utils.ParameterUtil;
import com.itee.bingsheng.pay.base.utils.http.HttpClient;
import com.itee.bingsheng.utils.MD5Util;
import com.itee.bingsheng.pay.base.utils.xml.XmlUtils;
import com.itee.bingsheng.pay.handler.ResponseHandler;
import com.itee.bingsheng.pay.weixin.bean.*;
import com.itee.bingsheng.pay.weixin.enums.TradeType;
import com.itee.bingsheng.pay.weixin.enums.WxOrderState;
import com.itee.bingsheng.pay.weixin.enums.WxpayConstants;
import com.itee.bingsheng.pay.weixin.service.WXPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class WXPayServiceImpl implements WXPayService {

	/**
	 * 初始日志类
	 */
	private Logger log = LoggerFactory.getLogger(WXPayServiceImpl.class);

	private String appId;								// 微信开发平台应用id
	private String mchId;								// 商户号
	private String partnerKey;						// 商户号对应的密钥

	private String reqUrl;								// 统一下单地址
	public String notifyUrl;							// 后台回调通知url

	private String orderQuerUrl;						// 查询订单接口地址

	private String closeOrderUrl;						// 关闭订单接口地址

	private String shorturl;							// 转换短链接接口地址

	private Integer orderExpireTime;					// 订单失效时间，单位：分钟

	@Override
	public CodeUrlBean getCodeUrl(OrderBean orderBean) throws Exception {
		CodeUrlBean codeUrlBean = new CodeUrlBean();
		try {
			String xmlStr = formatParam(orderBean);									   		// 格式化请求参数
			log.info("param:" + xmlStr);
			String response = HttpClient.doPostForXml(this.reqUrl, xmlStr);				// 发送请求
			log.info("return:" + response);
			CodeUrlReturnBean returnBean = XmlUtils.toObject(response, CodeUrlReturnBean.class);
			codeUrlBean.setCodeUrl(returnBean.getCodeUrl());
			codeUrlBean.setReturnCode(returnBean.getReturnCode());
			codeUrlBean.setReturnMsg(returnBean.getReturnMsg());
		}catch (Exception e){
			throw  new Exception(e);
		}
		return codeUrlBean;
	}

	/**
	 * 格式化参数，返回xml 形式
	 * @param orderBean
	 * @return
	 */
	private String formatParam(OrderBean orderBean) throws Exception {
		LinkedHashMap<String, String> packageParams = new LinkedHashMap<String, String>();
		String totalFee = AmountUtils.changeY2F(orderBean.getAmount()); 		// 总金额
//		String timeStart = CalendarUtils.formatDateToUnsigned(new Date());		// 交易时间
//		String timeExpire = CalendarUtils.formatDateToUnsigned(CalendarUtils.getDateByMinute(new Date(),orderExpireTime)); // 交易结束时间
		if(!StringUtils.isEmpty(orderBean.getNotifyUrl())){
			notifyUrl = orderBean.getNotifyUrl();
		}
		packageParams.put(WxpayConstants.APPID, this.appId); 									// 公众账号ID
		packageParams.put(WxpayConstants.MCH_ID, this.mchId);                                 // 商户号
		packageParams.put(WxpayConstants.NOTIFY_URL, notifyUrl);                              // 通知地址
		packageParams.put(WxpayConstants.BODY, orderBean.getRemark()); 							// 商品描述
		packageParams.put(WxpayConstants.OUT_TRADE_NO, orderBean.getOrderId()); 				// 商户订单号
		packageParams.put(WxpayConstants.SPBILL_CREATE_IP, orderBean.getSpbillCreateIp()); 	// 终端IP
		packageParams.put(WxpayConstants.TRADE_TYPE, TradeType.NATIVE.getKey()); 				// 交易类型
		packageParams.put(WxpayConstants.NONCE_STR, ParameterUtil.getRandomString(32)); 		// 随机字符串
		packageParams.put(WxpayConstants.TOTAL_FEE, totalFee);  	 							// 总金额
		packageParams.put(WxpayConstants.PRODUCT_ID,ParameterUtil.getOrderId());				// 商品ID
//		packageParams.put(WxpayConstants.TIME_START,timeStart ); 								// 交易时间
//		packageParams.put(WxpayConstants.TIME_EXPIRE,timeExpire); 								// 交易结束时间
		String signData = ParameterUtil.getSignData(packageParams);							 	// 将参数加密，为空参数不参与加密
		signData = signData +"&key=" + partnerKey;										 	// 商户key
		packageParams.put(WxpayConstants.SIGN, MD5Util.ecodeByMD5(signData).toUpperCase());		// 签名 -签名生成算法
		return XmlUtils.maptoXml(packageParams);
	}

	private   ShorturlBean  getShorturl(CodeUrlReturnBean returnBean) throws Exception {
		try {
			LinkedHashMap<String, String> packageParams = new LinkedHashMap<String, String>();
			packageParams.put(WxpayConstants.APPID, appId); 									// 公众账号ID
			packageParams.put(WxpayConstants.MCH_ID, mchId); 									// 商户号
			packageParams.put(WxpayConstants.NONCE_STR, ParameterUtil.getRandomString(32)); 			 	// 随机字符串

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
			signData = signData +"&key=" + partnerKey;										 // 商户key
			packageParams.put("sign", MD5Util.ecodeByMD5(signData).toUpperCase());				 // 签名 -签名生成算法
			String xmlStr = XmlUtils.maptoXml(packageParams);                                    // 转换xml格式参数
			String response = HttpClient.doPostForXml(shorturl, xmlStr);				     	// 发送请求
			ShorturlBean shorturlBean  = XmlUtils.toObject(response, ShorturlBean.class);		// 解析结果
			return shorturlBean;
		}catch (Exception e){
			throw  new Exception(e);
		}

	}

	/**
	 * 验证消息是否是微信发出的合法消息
	 * @return 验证结果
	 */
	private boolean verify(HttpServletRequest request,HttpServletResponse response){
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(this.partnerKey);
		//验证消息通知签名
		return resHandler.isTenpaySign();
	}

	/**
	 * 支付接口异步消息处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	public void handleNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			log.info("notify_url is params:"+XmlUtils.toXmlStr(request));
			//获得通知参数
			boolean reqState = verify(request, response);
			// 校验消息是否为微信发送
			if (reqState){
				// 处理后续业务,并通知微信
				sendSuccess(request, response);
			}
		}catch (Exception e){
			throw  new Exception(e);
		}
	}

	/**
	 * 通知微信处理结果
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void sendSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//获取微信xml参数
			NotifyReturnBean returnBean = XmlUtils.toObject(request, NotifyReturnBean.class);	   	// 开始解析xml文件
			//  根据通知结果，保存订单信息
			Map<String, String> resonseMap = saveOrderInfo(returnBean);
			// 格式化结果
			String xmlStr = XmlUtils.maptoXml(resonseMap);
			PrintWriter out =  response.getWriter();
			if(out != null){
				out.print(xmlStr);
				out.flush();
			}
		}catch (Exception e){
			throw new Exception(e);
		}
	}

	/**
	 * 保存订单信息
	 * @param returnBean
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> saveOrderInfo(NotifyReturnBean returnBean) throws Exception {
		Map<String,String> resonseMap = new HashMap<>();
		String returnMsg = "OK";
		String returnCode = ReturnCode.FAIL;
		// 根据返回的商户订单号，获取订单信息
/*		ErpOrderInfo erpOrderInfo = erpOrderInfoService.getOrderInfoByOrderId(returnBean.getOutTradeNo());
		if(null != erpOrderInfo ){
			switch (returnBean.getResultCode()){
				*//* 支付成功 *//*
				case ReturnCode.SUCCESS:
					returnCode = ReturnCode.SUCCESS;
					if(ErpOrderState.SUCCESS.getStateId() != erpOrderInfo.getStateId()){
						erpOrderInfo.setStateId(ErpOrderState.SUCCESS.getStateId());
						erpOrderInfo.setOrderCode(returnBean.getTransactionId());
						erpOrderInfo.setPayAccount(returnBean.getOpenId());
					}
					break;
				*//* 未支付或者支付失败 *//*
				case ReturnCode.FAIL:
					erpOrderInfo.setStateId(ErpOrderState.CLOSE.getStateId());
					erpOrderInfo.setRemark(returnBean.getReturnMsg());
					break;
			}
		}else{
			returnMsg = "未查询到商户对应的订单";
		}
		erpOrderInfo.setUpdateDate(new Date());
		erpOrderInfo.setRemark("微信支付结果通知："+returnBean.getReturnMsg());
		erpOrderInfo = erpOrderInfoService.saveOrderResult(erpOrderInfo);*/
		// 返回给微信的结果集
		resonseMap.put(ReturnCode.RETURN_CODE, returnCode);
		resonseMap.put(ReturnCode.RETURN_CODE, returnMsg);
		return resonseMap;
	}

	/**
	 * 查询订单状态接口
	 * @param orderInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public OrderQueryBean orderQuery(Order orderInfo) throws Exception {
		Map<String,Object> returnMap = new HashMap<>();
		LinkedHashMap<String, String> packageParams = new LinkedHashMap<String, String>();
		packageParams.put(WxpayConstants.APPID, this.appId); 									// 公众账号ID
		packageParams.put(WxpayConstants.MCH_ID, this.mchId); 									// 商户号
		packageParams.put(WxpayConstants.OUT_TRADE_NO, orderInfo.getOrderId()); 				// 商户订单号
		packageParams.put(WxpayConstants.NONCE_STR, ParameterUtil.getRandomString(32)); 		// 随机字符串
		String signData = ParameterUtil.getSignData(packageParams);							 	// 将参数加密，为空参数不参与加密
		signData = signData +"&key=" +partnerKey;										     	// 参数需要加上 商户key
		packageParams.put(WxpayConstants.SIGN, MD5Util.ecodeByMD5(signData).toUpperCase());		// 签名 -签名生成算法
		String xmlStr = XmlUtils.maptoXml(packageParams);                                       // 转换xml格式参数
		log.info("param:" + xmlStr);
		String response = HttpClient.doPostForXml(this.orderQuerUrl, xmlStr);				     // 发送请求
		log.info("return:" + response);
		OrderQueryBean orderQueryBean = XmlUtils.toObject(response, OrderQueryBean.class);	     // 解析返回结果
		switch (orderQueryBean.getTradeState()){
			case WxOrderState.NOTPAY:
				// 判断该订单是否已超时
//				if(CalendarUtils.getDatePoor(new Date(),orderInfo.getCreateDate()) > this.orderExpireTime){
//					// 关闭订单
//					ColseOrderReturnBean colseOrderReturnBean = closeOrder(orderInfo.getOrderId());
//					switch (colseOrderReturnBean.getReturnCode()){
//						case WxOrderState.SUCCESS:
//						case WxOrderState.ORDERCLOSED:
//							response = response.replace(orderQueryBean.getTradeState(),WxOrderState.CLOSED);
//							response = response.replace(orderQueryBean.getTradeStateDesc(),"订单超时被关闭");
//							orderQueryBean = XmlUtils.toObject(response, OrderQueryBean.class);	     // 重新解析返回结果
//							break;
//					}
//				}
				break;
		}
		return orderQueryBean;
	}

	/**
	 * 关闭订单接口
	 * @param orderId
	 */
	@Override
	public ColseOrderReturnBean closeOrder(String orderId) throws Exception {
		Map<String,Object> returnMap = new HashMap<>();
		LinkedHashMap<String, String> packageParams = new LinkedHashMap<String, String>();
		packageParams.put("appid", this.appId); 											 // 公众账号ID
		packageParams.put("mch_id", this.mchId); 										 	 // 商户号
		packageParams.put("out_trade_no", orderId); 										 // 商户订单号
		packageParams.put("nonce_str", ParameterUtil.getRandomString(32)); 				 // 随机字符串
		String signData = ParameterUtil.getSignData(packageParams);							 // 将参数加密，为空参数不参与加密
		signData = signData +"&key=" +partnerKey;										     // 参数需要加上 商户key
		packageParams.put("sign", MD5Util.ecodeByMD5(signData).toUpperCase());				 // 签名 -签名生成算法
		String xmlStr = XmlUtils.maptoXml(packageParams);                                    // 转换xml格式参数
		log.info("param:"+xmlStr);
		System.out.println("param:" + xmlStr);

		String response = HttpClient.doPostForXml(this.orderQuerUrl, xmlStr);				    // 发送请求
		log.info("return:" + response);
		ColseOrderReturnBean colseOrderReturnBean = XmlUtils.toObject(response, ColseOrderReturnBean.class);	     // 解析返回结果
		return colseOrderReturnBean;
	}


	public void setAppId(String appId) {this.appId = appId;}

	public void setMchId(String mchId) {this.mchId = mchId;}

	public void setPartnerKey(String partnerKey) {this.partnerKey = partnerKey;}

	public void setReqUrl(String reqUrl) {this.reqUrl = reqUrl;}

	public void setNotifyUrl(String notifyUrl) {this.notifyUrl = notifyUrl;}

	public void setOrderQuerUrl(String orderQuerUrl) { this.orderQuerUrl = orderQuerUrl; }

	public void setCloseOrderUrl(String closeOrderUrl) {
		this.closeOrderUrl = closeOrderUrl;
	}

	public void setShorturl(String shorturl) {this.shorturl = shorturl;}

	public void setOrderExpireTime(Integer orderExpireTime) {
		this.orderExpireTime = orderExpireTime;
	}
}
