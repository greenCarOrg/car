package com.itee.bingsheng.pay.weixin.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付XML解析类
 * @author Nick.Chan
 * 日期：2015年4月17日
 */
@XmlRootElement(name = "xml")
public class CodeUrlReturnBean {
	
	@XmlElement(name = "return_code")
	private String returnCode;
	
	@XmlElement(name = "return_msg")
	private String returnMsg;
	
	@XmlElement(name = "appid")
	private String appId;
	
	@XmlElement(name = "mch_id")
	private String mchId;
	
	@XmlElement(name = "nonce_str")
	private String nonceStr;
	
	@XmlElement(name = "sign")
	private String sign;
	
	@XmlElement(name = "result_code")
	private String resultCode;
	
	@XmlElement(name = "prepay_id")
	private String prepayId;
	
	@XmlElement(name = "trade_type")
	private String tradeType;
	
	// notify params
	
	@XmlElement(name = "attach")
	private String attach;
	
	@XmlElement(name = "bank_type")
	private String bankType;
	
	@XmlElement(name = "fee_type")
	private String feeType;
	
	@XmlElement(name = "is_subscribe")
	private String isSubScribe;
	
	@XmlElement(name = "openid")
	private String openId;
	
	@XmlElement(name = "out_trade_no")
	private String outTradeNo;
	
	@XmlElement(name = "sub_mch_id")
	private String subMchId;
	
	@XmlElement(name = "time_end")
	private String timeEnd;
	
	@XmlElement(name = "total_fee")
	private String totalFee;
	
	@XmlElement(name = "transaction_id")
	private String transactionId;
	
	@XmlElement(name = "cash_fee")
	private String cashFee;

	@XmlElement(name = "code_url")
	private String codeUrl;

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public String getAppId() {
		return appId;
	}

	public String getMchId() {
		return mchId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public String getAttach() {
		return attach;
	}

	public String getBankType() {
		return bankType;
	}

	public String getFeeType() {
		return feeType;
	}

	public String getIsSubScribe() {
		return isSubScribe;
	}

	public String getOpenId() {
		return openId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getCashFee() {
		return cashFee;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	@Override
	public String toString() {
		return "CodeUrlReturnBean [returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", appId=" + appId + ", mchId="
				+ mchId + ", nonceStr=" + nonceStr + ", sign=" + sign + ", resultCode=" + resultCode + ", prepayId="
				+ prepayId + ", tradeType=" + tradeType + ", attach=" + attach + ", bankType=" + bankType
				+ ", feeType=" + feeType + ", isSubScribe=" + isSubScribe + ", openId=" + openId + ", outTradeNo="
				+ outTradeNo + ", subMchId=" + subMchId + ", timeEnd=" + timeEnd + ", totalFee=" + totalFee
				+ ", transactionId=" + transactionId + ", cashFee=" + cashFee +", codeUrl=" + codeUrl +  "]";
	}

}
