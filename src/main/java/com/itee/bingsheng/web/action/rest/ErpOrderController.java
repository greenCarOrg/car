package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.entity.Order;
import com.itee.bingsheng.pay.base.bean.OrderBean;
import com.itee.bingsheng.pay.base.utils.IPUtils;
import com.itee.bingsheng.pay.weixin.bean.OrderQueryBean;
import com.itee.bingsheng.pay.weixin.enums.WxOrderState;
import com.itee.bingsheng.pay.weixin.service.WXPayService;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.utils.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


@RestController("nController")
@RequestMapping(value = "/rest")
public class ErpOrderController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(ErpOrderController.class);

    private ServletContext servletContext;
    @Resource
    WXPayService wxPayService;



    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    /**
     * 获取订单号
     * @return
     */
    @RequestMapping(value = "getOrderNum", method = RequestMethod.GET)
    public ExecuteResult<String> getOrderNum(){
        return new ExecuteResult(ResultCode.SUCCESS, DateUtils.getOrderNum());
    }

    @RequestMapping(value = "saveRecharge", method = RequestMethod.POST)
    public ExecuteResult<Order> saveRecharge(@RequestBody Order orderInfo) {
        ExecuteResult<Order> result = new ExecuteResult<Order>();
        try {
            /* 该订单未保存则先保存*/
        /*    if (null == orderInfo.getId()) {
                orderInfo.setStateId(ErpOrderState.DEFALUT.getStateId());                   // 设置默认支付状态
               orderInfo.setCreateDate(new Date());                                        // 创建时间
               orderInfo.setUpdateDate(new Date());							            // 更新时间
                orderInfo = erpOrderInfoService.saveOrderResult(orderInfo);             // 保存订单至数据库
            } else { // 订单已保存，查询该订单是否已处理
                orderInfo = erpOrderInfoService.getOrderInfoByOrderId(orderInfo.getOrderId());
                if (ErpOrderState.SUCCESS.getStateId() == orderInfo.getStateId()) {
                    result.setResult(orderInfo);
                    return result; // 直接返回成功
                }
            }
            switch (orderInfo.getPayType()) {
                case OrderPayType.WX_PAY: // 微信订单支付状态查询
                    result = wxOrderQuery(orderInfo);
                    break;
                case OrderPayType.ZFB_PAY: // 支付宝订单支付状态查询
                    result = zfbOrderQuery(orderInfo);
                    break;
            }
            *//* 更新订单状态及继续充值操作 *//*
            result.setResult(erpOrderInfoService.saveOrderResult(orderInfo));
           // final int studentId = orderInfo.getStudent().getStudentId();
*/
        } catch (Exception e) {
            logger.error("saveRecharge had error!", e);
            result.setResult(orderInfo);
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("系统异常,请联系管理员");
            return result;
        }
        return result;
    }


    /**
     * 微信订单处理
     *
     * @param orderInfo
     * @return
     * @throws Exception
     */
    private ExecuteResult<Order> wxOrderQuery(Order orderInfo) {
        ExecuteResult<Order> executeResult = new ExecuteResult<Order>();
        //查询订单支付状态
        OrderQueryBean orderQueryBean;
        try {
            orderQueryBean = wxPayService.orderQuery(orderInfo);
        } catch (Exception e) {
            logger.error("wxOrderQuery had error!", e);
            executeResult.setResultCode(ResultCode.ERROR);
            return executeResult;
        }
        switch (orderQueryBean.getTradeState()) {
            case WxOrderState.SUCCESS:
                orderInfo.setOrderCode(orderQueryBean.getTransactionId());  // 保存微信支付订单号
                orderInfo.setRemark("微信订单支付成功，确认充值");
                executeResult.setResultCode(ResultCode.SUCCESS);
                break;
            case WxOrderState.CLOSED:     // 订单关闭
            case WxOrderState.REFUND:     // 转入退款
            case WxOrderState.REVOKED:       // 订单撤销
            case WxOrderState.PAYERROR:   // 支付失败
                orderInfo.setRemark(orderQueryBean.getTradeStateDesc()); // 失败描述
                orderInfo.setUpdateDate(new Date());
                executeResult.setResultCode(ResultCode.FAIL);
                executeResult.setMessage(orderQueryBean.getTradeStateDesc());
                break;
            default:
                executeResult.setResultCode(ResultCode.FAIL);
                executeResult.setMessage(orderQueryBean.getTradeStateDesc());
                break;

        }
        return executeResult;
    }

    /**
     * 生成手机支付订单
     * @param request
     * @param orderBean
     * @return
     */
    @RequestMapping(value = "/viewOrderInfo", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult unifiedOrder(HttpServletRequest request, @RequestBody OrderBean orderBean) {
        ExecuteResult result = new ExecuteResult<>();
        Map<String, Object> data = new HashedMap();
        try {
            orderBean.setSpbillCreateIp(IPUtils.getIpAddr(request));
            switch (orderBean.getPayType()) {
                case 2:
                    data.put("returnBean", wxPayService.getCodeUrl(orderBean));                  // 调用微信统一下单接口，生成微信订单
                    break;
                case 3:
                    // data.put("returnBean", aliPayService.getCodeUrl(orderBean));                // 调用支付宝预下单接口，生成支付宝订单
                    break;
            }
            data.put("orderInfo", orderBean);
            data.put("payType", orderBean.getPayType());
            data.put("payTypeText", orderBean.getPayType());
            result.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error("unifiedOrder had error!", e);
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("系统错误");
            return result;
        }
        result.setResult(data);
        return result;
    }
}
