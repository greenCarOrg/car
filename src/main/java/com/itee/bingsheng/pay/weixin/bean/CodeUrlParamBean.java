package com.itee.bingsheng.pay.weixin.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付XML解析类
 * @author Nick.Chan
 * 日期：2015年4月17日
 */
public class CodeUrlParamBean {

	private String appId; 				// 公众账号ID		必填：是	备注：微信分配的公众账号ID
	private String mchId;				// 商户号      		必填：是	备注：微信支付分配的商户号
	private String deviceInfo;		// 设备号      		必填：否	备注: 终端设备号(门店号或收银设备ID)：
	private String nonceStr;			// 随机字符串    	必填：是	备注：随机字符串，不长于32位：
	private String sign;				// 签名       		必填：是	备注：签名算法生成的字符串
	private String body;				// 商品描述     	必填：是	备注：商品或支付单简要描述
	private String detail;				// 商品详情     	必填：否	备注：商品名称明细列表
	private String attach;				// 附加数据     	必填：否	备注：在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	private String outTradeNo;		// 商户订单号    	必填：是	备注: 商户系统内部的订单号,32个字符内、可包含字母;
	private String feeType;			// 货币类型     	必填：否	备注：默认CNY，人民币
	private Double totalFee;			// 总金额      		必填：是	备注：单位需要转为分;
	private String spbillCreateIp;	// 终端IP     		必填：是	备注：APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	private String timeStart;			// 交易起始时间     必填：否	备注：订单生成时间，格式为yyyyMMddHHmmss
	private String timeExpire;		// 交易结束时间     必填：否	备注：订单失效时间，格式为yyyyMMddHHmmss
	private String goodsTag;			// 商品标记     	必填：否	备注：代金券或立减优惠功能的参数
	private String notifyUrl;			// 通知地址     	必填：是	备注：接收微信支付异步通知回调地址(无参数)
	private String tradeType;			// 交易类型     	必填：是	备注：取值如下：JSAPI，NATIVE，APP
	private String productId;			// 商品ID     		必填：否	备注：trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义
	private String limitPay;			// 指定支付方式     必填：否	备注：no_credit--指定不能使用信用卡支付
	private String openid;				// 用户标识     	必填：否	备注：trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识

	public CodeUrlParamBean() {
		super();
	}

	/**
	 * 微信扫码支付必要参数构造
	 * @param appId			公众账号ID
	 * @param mchId			商户号
	 * @param nonceStr			随机字符串
	 * @param body				商品描述
	 * @param outTradeNo		商户订单号
	 * @param totalFee			总金额
	 * @param spbillCreateIp  终端IP
	 * @param notifyUrl		通知地址
	 * @param tradeType		交易类型:NATIVE
	 * @param productId		商品ID
	 * @param sign				签名
	 */
	public CodeUrlParamBean(String appId, String mchId, String nonceStr, String body, String outTradeNo, Double totalFee, String spbillCreateIp, String notifyUrl, String tradeType, String productId, String sign) {
		this.appId = appId;
		this.mchId = mchId;
		this.nonceStr = nonceStr;
		this.body = body;
		this.outTradeNo = outTradeNo;
		this.totalFee = totalFee;
		this.spbillCreateIp = spbillCreateIp;
		this.notifyUrl = notifyUrl;
		this.tradeType = tradeType;
		this.productId = productId;
		this.sign = sign;
	}

	public String getAppId() { return appId;}

	public void setAppId(String appId) { this.appId = appId;}

	public String getMchId() { return mchId;}

	public void setMchId(String mchId) {this.mchId = mchId;}

	public String getDeviceInfo() {return deviceInfo;}

	public void setDeviceInfo(String deviceInfo) {this.deviceInfo = deviceInfo;}

	public String getNonceStr() {return nonceStr;}

	public void setNonceStr(String nonceStr) {this.nonceStr = nonceStr;}

	public String getSign() {return sign;}

	public void setSign(String sign) {this.sign = sign;}

	public String getBody() {return body;}

	public void setBody(String body) {this.body = body;}

	public String getDetail() {return detail;}

	public void setDetail(String detail) {this.detail = detail;}

	public String getAttach() {return attach;}

	public void setAttach(String attach) {this.attach = attach;}

	public String getOutTradeNo() {return outTradeNo;}

	public void setOutTradeNo(String outTradeNo) {this.outTradeNo = outTradeNo;}

	public String getFeeType() {return feeType;}

	public void setFeeType(String feeType) {this.feeType = feeType;}

	public Double getTotalFee() {return totalFee;}

	public void setTotalFee(Double totalFee) {this.totalFee = totalFee;}

	public String getSpbillCreateIp() {return spbillCreateIp;}

	public void setSpbillCreateIp(String spbillCreateIp) {this.spbillCreateIp = spbillCreateIp;}

	public String getTimeStart() {return timeStart;}

	public void setTimeStart(String timeStart) {this.timeStart = timeStart;}

	public String getTimeExpire() {return timeExpire;}

	public void setTimeExpire(String timeExpire) {this.timeExpire = timeExpire;}

	public String getGoodsTag() {return goodsTag;}

	public void setGoodsTag(String goodsTag) {this.goodsTag = goodsTag;}

	public String getNotifyUrl() {return notifyUrl;}

	public void setNotifyUrl(String notifyUrl) {this.notifyUrl = notifyUrl;}

	public String getTradeType() {return tradeType;}

	public void setTradeType(String tradeType) {this.tradeType = tradeType;}

	public String getProductId() {return productId;}

	public void setProductId(String productId) {this.productId = productId;}

	public String getLimitPay() {return limitPay;}

	public void setLimitPay(String limitPay) {this.limitPay = limitPay;}

	public String getOpenid() {return openid;}

	public void setOpenid(String openid) {this.openid = openid;}

	public String toString(){
		StringBuffer sdf = new StringBuffer();
		this.getClass().getFields();
		return sdf.toString();
	}
}
