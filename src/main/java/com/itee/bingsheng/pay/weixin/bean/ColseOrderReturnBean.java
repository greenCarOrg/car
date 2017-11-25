package com.itee.bingsheng.pay.weixin.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by abc on 2016/3/18.
 */
@XmlRootElement(name = "xml")
public class ColseOrderReturnBean {

    @XmlElement(name = "return_code")
    private String returnCode; 	// 返回状态码,必填：是 ,SUCCESS/FAIL

    @XmlElement(name = "return_msg")
    private String returnMsg; 	// 返回信息,必填：否

    @XmlElement(name = "appid")
    private String appid; 			// 公众账号ID,必填：是

    @XmlElement(name = "mch_id")
    private String mchId; 		// 商户号,必填：是

    @XmlElement(name = "nonce_str")
    private String nonceStr; 	// 随机字符串,必填：是

    @XmlElement(name = "sign")
    private String sign; 			// 签名,必填：是

    @XmlElement(name = "err_code")
    private String errCode; 		// 错误代码,必填：否

    @XmlElement(name = "err_code_des")
    private String errCodeDes;  // 错误代码描述,必填：否

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public String getAppid() {
        return appid;
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

    public String getErrCode() {
        return errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }
}
