package com.itee.bingsheng.pay.koclawallet.service;

import com.itee.bingsheng.pay.base.bean.CodeUrlBean;
import com.itee.bingsheng.pay.base.bean.OrderBean;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface KoclawalletService {
    /**
     * 网二维码支付接口
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
    public Object handleNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;

}