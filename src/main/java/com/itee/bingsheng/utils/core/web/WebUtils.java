package com.itee.bingsheng.utils.core.web;

import com.itee.bingsheng.utils.core.metatype.Dto;
import com.itee.bingsheng.utils.core.metatype.impl.BaseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * 和Web层相关的实用工具类
 */
public class WebUtils {

	/**
	 * 将请求参数封装为Dto
	 * 
	 * @param request
	 * @return
	 */
	public static Dto getParamAsDto(HttpServletRequest request) {
		Dto dto = new BaseDto();
		@SuppressWarnings("rawtypes")
		Map map = request.getParameterMap();
		@SuppressWarnings("rawtypes")
		Iterator keyIterator = map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			dto.put(key, value);
		}
		return dto;
	}




}
