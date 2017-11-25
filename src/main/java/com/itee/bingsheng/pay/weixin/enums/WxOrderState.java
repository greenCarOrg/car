package com.itee.bingsheng.pay.weixin.enums;

/**
 * Created by abc on 2016/3/2.
 */
public interface WxOrderState {

    public static final String SUCCESS = "SUCCESS";          // 支付成功
    public static final String REFUND = "REFUND";            // 转入退款
    public static final String NOTPAY = "NOTPAY";			 // 未支付
    public static final String CLOSED = "CLOSED";			 // 已关闭
    public static final String REVOKED = "REVOKED";		     // 已撤销（刷卡支付）
    public static final String USERPAYING = "USERPAYING";   // 用户支付中
    public static final String PAYERROR = "PAYERROR";		 // 支付失败(其他原因，如银行返回失败)
    public static final String ORDERCLOSED = "ORDERCLOSED"; // 订单已关闭

}
