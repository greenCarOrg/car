package com.itee.bingsheng.pay.base.enums;

/**
 * Created by abc on 2016/2/25.
 */
public enum PayTypeEnum implements BaseEnum {
    ALL("0", 0,"全部","all","all"),
    ZZ("7", 7,"转账","zz","转账"),
    XJZF("1", 1,"现金支付","","现金"),
    WXZF("2", 2,"微信支付","weixin","微信"),
    ZFBZF("3",3, "支付宝支付","alipay","支付宝"),
    SKZF("5",5, "刷卡支付","SKZF","刷卡"),
    QT("6",6, "其他","other","其他");


    protected String key;

    protected int payType;

    protected String value;

    protected String payTypeCode;

    protected String payTypeText;


    private PayTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private PayTypeEnum(String key, String value, String payTypeCode, String payTypeText) {
        this.key = key;
        this.value = value;
        this.payTypeCode = payTypeCode;
        this.payTypeText = payTypeText;
    }

    private PayTypeEnum(String key, int payType,String value, String payTypeCode, String payTypeText) {
        this.key = key;
        this.payType = payType;
        this.value = value;
        this.payTypeCode = payTypeCode;
        this.payTypeText = payTypeText;
    }


    public String getValue() {
        return value;
    }

    public int getPayType(){return payType;}

    public String getKey() {
        return this.key;
    }

    public String getPayTypeCode() {
        return this.payTypeCode;
    }

    public String getPayTypeText() {
        return this.payTypeText;
    }

    public static String getValueByKey(String key) {
        String resut = "";
        for (PayTypeEnum eum : PayTypeEnum.values()) {
            if (key.equals(eum.getKey())) {
                return eum.getValue();
            }
        }
        return resut;
    }


    public static String getValueByPayType(int payType) {
        String resut = "";
        for (PayTypeEnum eum : PayTypeEnum.values()) {
            if (payType == eum.getPayType()) {
                return eum.getValue();
            }
        }
        return resut;
    }

    public static String getPayTypeTextByKey(String key) {
        String resut = "";
        for (PayTypeEnum eum : PayTypeEnum.values()) {
            if (key.equals(eum.getKey())) {
                return eum.getPayTypeText();
            }
        }
        return resut;
    }

    public static PayTypeEnum getPayTypeEnumByPayType(int payType){
        PayTypeEnum returnPayTypeEnum = null;
        for (PayTypeEnum eum : PayTypeEnum.values()) {
            if (payType == eum.payType) {
                return eum;
            }
        }
        return returnPayTypeEnum;
    }

    public static PayTypeEnum getPayTypeEnumByKey(String key){
        PayTypeEnum returnPayTypeEnum = null;
        for (PayTypeEnum eum : PayTypeEnum.values()) {
            if (key.equals(eum.getKey())) {
                return eum;
            }
        }
        return returnPayTypeEnum;
    }

    public String toString() {
        return this.getValue();
    }
}
