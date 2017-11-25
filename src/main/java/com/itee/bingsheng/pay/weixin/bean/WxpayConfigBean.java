package com.itee.bingsheng.pay.weixin.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付相关配置
 * @author HuangXiaobin
 *
 */
@SuppressWarnings("serial")
public class WxpayConfigBean implements Serializable{

	/**
	 * 公共配置
	 */
	private Map<String,Object> configMaps = new HashMap<String,Object>();

	public Map<String, Object> getConfigMaps() {
		return configMaps;
	}

	public void setConfigMaps(Map<String, Object> configMaps) {
		this.configMaps = configMaps;
	}

	public WxpayConfigBean() {
		super();
	}
	
	public WxpayConfigBean(Map<String, Object> configMaps) {
		super();
		this.configMaps = configMaps;
	}
	
	public Object get(String key){
		if(this.configMaps != null){
			return this.configMaps.get(key);
		}
		return null;
	}
}
