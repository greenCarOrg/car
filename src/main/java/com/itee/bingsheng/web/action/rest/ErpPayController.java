package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.pay.base.bean.OrderBean;
import com.itee.bingsheng.pay.koclawallet.service.KoclawalletService;
import com.itee.bingsheng.pay.weixin.demo.Demo;
import com.itee.bingsheng.pay.weixin.service.WXPayService;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.web.websocket.WebsocketService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@RestController("newErpPayController")
@RequestMapping(value = "/rest/pay")
public class ErpPayController {
    /**
     * 初始日志类
     */
    private Logger logger = LoggerFactory.getLogger(ErpPayController.class);

    @Resource
    WXPayService wxPayService;
    @Resource
    KoclawalletService koclawalletService;



    @RequestMapping(value = "")
    public String init(HttpServletRequest request) throws Exception {
        String url = Demo.getCodeurl();
        request.getSession().setAttribute("code_url", url);
        return "pay/orderInfoPage";
    }

    @RequestMapping(value = "/viewOrderInfo", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult unifiedOrder(HttpServletRequest request,@RequestBody OrderBean orderBean) {
        ExecuteResult result = new ExecuteResult<>();
        Map<String, Object> data = new HashedMap();
        try {
            /*if (!"view".equals(request.getParameter("windowType"))) {
                orderBean.setSchool(teachService.getSchoolById(orderBean.getSchool().getSchoolId()));             // 教学点信息
                orderBean.setStudent(studentService.getStudenByStudentId(orderBean.getStudent().getStudentId())); // 学生信息
                //orderBean.setOrderId(DateUtils.getOrderNum());// 订单号，改成前端设置值
                orderBean.setSpbillCreateIp(IPUtils.getIpAddr(request));                                            // 终端IP

                if ("1".equals(orderBean.getTradeType())) {
                    orderBean.setSubject("超课学员[" + orderBean.getStudent().getStudentName() + "]账户充值");   // 订单标题
                    orderBean.setRemark("超课学员[" + orderBean.getStudent().getStudentName() + "]账户充值");    // 商品描述
                }
                switch (orderBean.getPayType()) {
                    case OrderPayType.WX_PAY: // 微信订单支付状态查询
                        data.put("returnBean", wxPayService.getCodeUrl(orderBean));                  // 调用微信统一下单接口，生成微信订单
                        break;
                    case OrderPayType.ZFB_PAY: // 支付宝订单支付状态查询
                        data.put("returnBean", aliPayService.getCodeUrl(orderBean));                // 调用支付宝预下单接口，生成支付宝订单
                        break;
                }
                PayTypeEnum payTypeEnum = PayTypeEnum.getPayTypeEnumByPayType(orderBean.getPayType());            // 支付类型
                data.put("orderInfo", orderBean);
                data.put("payType", payTypeEnum.getKey());
                data.put("payTypeText", payTypeEnum.getPayTypeText());
            } else {
                CodeUrlBean returnBean = new CodeUrlBean();
                returnBean.setReturnCode("SUCCESS");
                data.put("returnBean", returnBean);
            }*/
        } catch (Exception e) {
            logger.error("unifiedOrder had error!", e);
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("系统错误");
            return result;
        }
        result.setResult(data);
        return result;
    }

    /**
     * 接受微信支付回调通知
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/weixinNotify/{key}")
    public void wxNotify(@PathVariable String key,HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("微信支付回调.........");
        try {
            wxPayService.handleNotify(request, response);

            //通知前端关闭页面
            try {
                WebsocketService.sendMsg(key);
            } catch (IOException e) {
                logger.info("支付成功回调通知客户端失败");
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }



    @RequestMapping(value = "/paymentNotice/{key}")
    @ResponseBody
    public String paymentNotice(@PathVariable String key,HttpServletRequest request, HttpServletResponse response){
        try {
            WebsocketService.sendMsg(key);
        } catch (IOException e) {
            logger.info("支付成功回调通知客户端失败");
            e.printStackTrace();
        }
        return "success";
    }



}
