package com.itee.bingsheng.pay.weixin.enums;

import com.itee.bingsheng.pay.base.enums.BaseEnum;

/**
 * 微信支付方式
 * Created by abc on 2016/3/1.
 */
public enum TradeType implements BaseEnum {

    APP("APP","app支付"),
    JSAPI("JSAPI","公众号支付"),
    NATIVE("NATIVE","原生扫码支付");

    protected String key;

    protected String value;

    public String getKey() {
        return this.key;
    }

    public String getValue() {  return value;  }


    private TradeType(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
