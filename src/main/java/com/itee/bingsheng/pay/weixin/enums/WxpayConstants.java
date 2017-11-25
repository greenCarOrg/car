package com.itee.bingsheng.pay.weixin.enums;

/**
 * Created by abc on 2016/3/17.
 */
public interface WxpayConstants {

    public static final String APPID="appid";							// 公众账号ID

    public static final String MCH_ID="mch_id";                  		// 商户号

    public static final String NOTIFY_URL="notify_url";            	    // 通知地址

    public static final String BODY="body"; 								// 商品描述

    public static final String OUT_TRADE_NO="out_trade_no"; 			// 商户订单号

    public static final String SPBILL_CREATE_IP="spbill_create_ip"; 	// 终端IP

    public static final String TRADE_TYPE="trade_type"; 				// 交易类型

    public static final String NONCE_STR="nonce_str"; 				 	// 随机字符串

    public static final String TOTAL_FEE="total_fee";  	 				// 总金额

    public static final String PRODUCT_ID="product_id";					// 商品ID

    public static final String TIME_START="time_start"; 				// 交易时间

    public static final String TIME_EXPIRE="time_expire"; 				// 交易结束时间

    public static final String SIGN="sign";				 				// 签名 -签名生成算法

}
