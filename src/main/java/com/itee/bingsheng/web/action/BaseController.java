package com.itee.bingsheng.web.action;

import com.itee.bingsheng.utils.core.metatype.Dto;
import com.itee.bingsheng.utils.core.metatype.impl.BaseDto;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.utils.core.web.WebUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 基础Controller
 */
public class BaseController {

	@Autowired
	private HttpServletRequest request;

	private Class<?> clazz; // 对应的实体
	protected Map<String, Object> map = new HashMap<String, Object>();
	protected Dto params = new BaseDto();
	protected String code;
	protected String msg;
	protected JSONObject result=new JSONObject();//封装接口固定结构



	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Dto getParams() {
		return params;
	}

	public void setParams(Dto params) {
		this.params = params;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request) {
		this.request = request;
		this.params = WebUtils.getParamAsDto(this.request);
		BL3Utils.mapRemoveNull(this.params);
	}
	public JSONObject getResult(){
		result.put("code",code);
		result.put("msg",msg);
		return result;
	}

	/**校验参数
	 * @param params：参数
	 * @param fields：map中的key组成的字符串数组
	 */
	public void checkParam(Map<String,Object> params,String[] fields) {
		params=BL3Utils.treeMapToMap(params,new HashMap<String, Object>());
		for (String field: fields) {
			if(!params.containsKey(field)) {
				throw new IllegalArgumentException("App请求少传参数: " + field);
			}
			if (params.get(field) == null || "".equals(params.get(field).toString().trim())||"(null)".equals(params.get(field).toString().trim())) {
				throw new IllegalArgumentException("App请求参数: " + field + " 的值为空!");
			}
		}
	}
}