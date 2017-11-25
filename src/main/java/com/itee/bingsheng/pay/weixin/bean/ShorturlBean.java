package com.itee.bingsheng.pay.weixin.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 转换短链接bean
 * Created by abc on 2016/3/1.
 */
@XmlRootElement(name = "xml")
public class ShorturlBean {

    // 公众账号ID,必填：是
    @XmlElement(name = "appid")
    private String appId;

    // 商户号,必填：是
    @XmlElement(name = "mch_id")
    private String mchId;

    // 随机字符串,必填：是
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    // 签名,必填：是
    @XmlElement(name = "sign")
    private String sign;

    // 业务结果, 必填：是
    @XmlElement(name = "result_code")
    private String resultCode;

    // 错误代码,必填：否
    @XmlElement(name = "err_code")
    private String errCode;

    // 转换后的URL链接,必填：是
    @XmlElement(name = "short_url")
    private String shortUrl;

    // 返回信息,必填：否
    @XmlElement(name = "return_msg")
    private String returnMsg;


    public ShorturlBean() {
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

    public String getErrCode() {
        return errCode;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

}
