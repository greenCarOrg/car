package com.itee.bingsheng.pay.weixin.service;

import com.itee.bingsheng.entity.Order;
import com.itee.bingsheng.pay.base.bean.CodeUrlBean;
import com.itee.bingsheng.pay.base.bean.OrderBean;
import com.itee.bingsheng.pay.weixin.bean.ColseOrderReturnBean;
import com.itee.bingsheng.pay.weixin.bean.OrderQueryBean;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WXPayService {
	
	/**
	 * 统一下单
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public CodeUrlBean getCodeUrl(OrderBean orderBean) throws Exception;


	/**
	 * 支付接口异步消息处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void handleNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;


	/**
	 * 查询微信支付订单
	 * @throws Exception
	 */
	public OrderQueryBean orderQuery(Order orderInfo) throws Exception;


	/**
	 * 关闭微信支付订单
	 * @throws Exception
	 */
	public ColseOrderReturnBean closeOrder(String orderCode) throws Exception;
}
