package com.itee.bingsheng.pay.weixin.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by abc on 2016/3/2.
 */
@XmlRootElement(name = "xml")
public class OrderQueryBean {

    @XmlElement(name = "appid")
    private String appId;                        // 公众账号ID,必填：是

    @XmlElement(name = "mch_id")
    private String mchId; 							// 商户号,必填：是

    @XmlElement(name = "nonce_str")
    private String nonceStr; 					// 随机字符串,必填：是

    @XmlElement(name = "sign")
    private String sign; 								// 签名,必填：是

    @XmlElement(name = "result_code")
    private String resultCode; 				// 业务结果 ,必填：是

    @XmlElement(name = "err_code")
    private String errCode; 						// 错误代码,必填：否

    @XmlElement(name = "openid")
    private String openId; 							// 用户标识,必填：是

    @XmlElement(name = "trade_type")
    private String tradeType; 					// 交易类型 ,必填：是

    @XmlElement(name = "trade_state")
    private String tradeState; 				// 交易状态 ,必填：是

    @XmlElement(name = "bank_type")
    private String bankType; 					// 付款银行,必填：是

    @XmlElement(name = "total_fee")
    private int totalFee; 							// 总金额,必填：是

    @XmlElement(name = "cash_fee")
    private int cashFee; 							// 现金支付金额,必填：是

    @XmlElement(name = "transaction_id")
    private String transactionId; 			// 微信支付订单号,必填：是

    @XmlElement(name = "out_trade_no")
    private String outTradeNo; 				// 商户订单号,必填：是

    @XmlElement(name = "time_end")
    private String timeEnd; 						// 支付完成时间,必填：是

    @XmlElement(name = "trade_state_desc")
    private String tradeStateDesc; 		      // 交易状态描述,必填：是

    @XmlElement(name = "return_code")
    private String returnCode;                 // 返回状态码 是

    @XmlElement(name = "err_code_des")
    private String errCodeDes; 				// 错误代码描述,必填：否

    @XmlElement(name = "device_info")
    private String deviceInfo; 				// 设备号,必填：否

    @XmlElement(name = "is_subscribe")
    private String isSubscribe; 				// 是否关注公众账号,必填：否

    @XmlElement(name = "fee_type")
    private String feeType; 						// 货币种类,必填：否


    @XmlElement(name = "cash_fee_type")
    private String cashFeeType; 			// 现金支付货币类型,必填：否

    @XmlElement(name = "coupon_fee")
    private int couponFee; 						// 代金券或立减优惠金额 ,必填：否

    @XmlElement(name = "coupon_count")
    private int couponCount; 					// 代金券或立减优惠使用数量,必填：否

    @XmlElement(name = "attach")
    private String attach; 							// 附加数据,必填：否

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

    public String getReturnCode() {
        return returnCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getOpenId() {
        return openId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public String getBankType() {
        return bankType;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public int getCashFee() {
        return cashFee;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public int getCouponFee() {
        return couponFee;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public String getAttach() {
        return attach;
    }
}
