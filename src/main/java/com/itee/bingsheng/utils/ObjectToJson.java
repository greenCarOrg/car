package com.itee.bingsheng.utils;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ObjectToJson {
	/**
	 * Can be a single object or collection of objects
	 * @param o
	 * @return
	 */ 
	public static String objectToJson(Object o){
		try {
			if(o!=null){ 
				if(o instanceof Collection){
					return JSONArray.fromObject(o).toString();
				}else{
					return JSONObject.fromObject(o).toString();
				}
			}else{
				MessageInfo info = null;
				info = new MessageInfo("数据为空","-1");
				return JSONObject.fromObject(info).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageInfo info = null;
			info = new MessageInfo("返回数据出现异常","-2");
			return JSONObject.fromObject(info).toString();
		}
	}
}
