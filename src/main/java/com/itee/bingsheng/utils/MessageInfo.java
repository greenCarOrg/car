package com.itee.bingsheng.utils;


public class MessageInfo {
	private String value;
	private String msg;
	public String getValue() {
		return value;
	} 
	public void setValue(String value) {
		this.value = value;
	}
	public String getMsg() {
		return msg; 
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public MessageInfo(String value, String msg) {
		super();
		this.value = value;
		this.msg = msg;
	}
}
