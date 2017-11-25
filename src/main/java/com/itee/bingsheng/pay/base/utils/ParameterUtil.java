/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.itee.bingsheng.pay.base.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 参数构造工具类
 * 
 * @author chenxiaoqin
 * @version $Id: ParameterUtil.java
 */
public class ParameterUtil {

    /**
     * 将Map组装成待签名数据。
     * 待签名的数据必须按照一定的顺序排列 这个是支付宝提供的服务的规范，否则调用支付宝的服务会通不过签名验证
     * @param params
     * @return
     */
    public static String getSignData(Map<String, String> params) {
        StringBuffer content = new StringBuffer();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)||"sign_type".equals(key) || "signType".equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null) {
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            } else {
                content.append((i == 0 ? "" : "&") + key + "=");
            }
        }

        return content.toString();
    }
    
    /**
     * 将Map组装成待签名数据。
     * 待签名的数据必须按照一定的顺序排列 这个是支付宝提供的服务的规范，否则调用支付宝的服务会通不过签名验证
     * @param params
     * @return
     */
    public static String getSignDataNoBlank(Map<String, String> params) {
        StringBuffer content = new StringBuffer();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)||"sign_type".equals(key) || "signType".equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null && !"".equals(value)) {
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            }

        }

        return content.toString();
    }

    /**
     * 将Map中的数据组装成url
     * @param params
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
        return mapToUrl(params, "UTF-8");
    }
    
    /**
     * 根据指定编码对参数进行编码
     * @param params
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String mapToUrl(Map<String, String> params, String charset) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (isFirst) {
                sb.append(key + "=" + URLEncoder.encode(value, charset));
                isFirst = false;
            } else {
                if (value != null) {
                    sb.append("&" + key + "=" + URLEncoder.encode(value, charset));
                } else {
                    sb.append("&" + key + "=");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 取得URL中的参数值。
     * <p>如不存在，返回空值。</p>
     * 
     * @param url
     * @param name
     * @return
     */
    public static String getParameter(String url, String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        name = name + "=";
        int start = url.indexOf(name);
        if (start < 0) {
            return null;
        }
        start += name.length();
        int end = url.indexOf("&", start);
        if (end == -1) {
            end = url.length();
        }
        return url.substring(start, end);
    }

    public static void main(String [] args){
        System.out.println(getOrderId());
    }

    /**
     * 获取订单号
     * @return
     */
    public static String getOrderId() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        Random rand = new Random();
        int i = rand.nextInt(); //int范围类的随机数
        i = rand.nextInt(999); //生成0-100以内的随机数
        return df.format(date)+""+ i;
    }

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

	public static String getUTF8ValueByMap(Map<String, Object> map, String key, String defaultV){
		String value = defaultV;
		try {
			value = map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? map.get(key.trim()).toString().trim() : defaultV;
		} catch (Exception e) {
			value = defaultV;
		}
		return getStrUTF8(value);
	}
	
	public static String getValueByMap(Map<String, Object> map, String key, String defaultV){
		try {
			return map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? map.get(key.trim()).toString().trim() : defaultV;
		} catch (Exception e) {
			return defaultV;
		}
	}
	
	public static Integer getIntegerValueByMap(Map<String, Object> map, String key, Integer defaultV){
		try {
			return map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? Integer.valueOf(map.get(key.trim()).toString().trim()) : defaultV;
		} catch (Exception e) {
			return defaultV;
		}
	}
	
	public static double getDoubleValueByMap(Map<String, Object> map, String key, double defaultV){
		try {
			return map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? Double.valueOf(map.get(key.trim()).toString().trim()) : defaultV;
		} catch (Exception e) {
			return defaultV;
		}
	}
    
	public static String getStrUTF8(String str){
		try {
			str = !str.equals("") ? new String(str.getBytes("ISO-8859-1")) : "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
    
	/**
	 * 将value为数组类型的map转换成普通Map
	 * @param map	Map<String, String[]>
	 * @return		Map<String, Object>
	 */
	public static Map<String, Object> conversionToGeneralMap(Map<String, String[]> map){
		Map<String, Object> generalMap = new HashMap<String, Object>();
		String key = "";
		String value = "";
		for(Entry<String, String[]> entry : map.entrySet()){
			key = ""; value = "";
			key = entry.getKey();
			Object object = entry.getValue();
			if(object == null){
				value = "";
			}else if(object instanceof String[]){
				String[] array = (String[])object;
				for (int i = 0; i < array.length; i++) {
					value = array[i] + "";
				}
			}else{
				value = object.toString();
			}
			generalMap.put(key, value);
		}
		return generalMap;
	}
	
	public static Map<String, Object> getParameterByRequest(HttpServletRequest req){
		if(req != null){
			Map<String, String[]> paraMap = req.getParameterMap();
			Map<String, Object> paraMap_s = new HashMap<String, Object>();
			if(paraMap != null)
				paraMap_s = conversionToGeneralMap(paraMap);
			return paraMap_s;
		}
		return null;
	}

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }


    /**
     * 获取编码字符集
     * @param request
     * @param response
     * @return String
     */
    public static String getCharacterEncoding(HttpServletRequest request,
                                              HttpServletResponse response) {
        if (null == request || null == response) {
            return "gbk";
        }
        String enc = request.getCharacterEncoding();
        if (null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }

        if (null == enc || "".equals(enc)) {
            enc = "gbk";
        }
        return enc;
    }

    /**
     * 将地址格式化
     * @param sourceUrl
     * @return
     */
    public static String urlEncodeUTF8(String sourceUrl){
        String result = sourceUrl;
        try {
            result = java.net.URLEncoder.encode(sourceUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
