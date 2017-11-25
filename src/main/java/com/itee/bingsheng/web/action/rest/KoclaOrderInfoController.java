package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.entity.KoclaOrderInfo;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by abc on 2015/12/14.
 */
@RestController("newKoclaOrderInfoController")
@RequestMapping(value = "/rest")
public class KoclaOrderInfoController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(KoclaOrderInfoController.class.getName());


    /**
     * 验证订单
     * @param koclaOrderInfo
     * @return
     */
    @RequestMapping(value = "validateOrder", method = RequestMethod.POST)
    public ExecuteResult<KoclaOrderInfo> validateOrder(@RequestBody KoclaOrderInfo koclaOrderInfo) {
        ExecuteResult<KoclaOrderInfo> executeResult = null;
        try {
//             koclaOrderInfo = koclaOrderInfoService.validateOrder(koclaOrderInfo);
//            if (koclaOrderInfo != null) {
//                executeResult = new ExecuteResult<KoclaOrderInfo>(ResultCode.SUCCESS, koclaOrderInfo);
//            } else {
//                executeResult = new ExecuteResult<KoclaOrderInfo>(ResultCode.FAIL,koclaOrderInfo);
//            }
        } catch (Exception e) {
            logger.error("validateOrder is error:" + e);
            executeResult = new ExecuteResult<KoclaOrderInfo>(ResultCode.ERROR,koclaOrderInfo);
            executeResult.setMessage(e.getMessage() == null ? "内部报错" : e.getMessage());
        }finally {
            return executeResult;
        }

    }

    /**
     * 更新订单中的学位信息
     * @param orderId
     * @param studentId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateDegreeInfoByOrderInfo", method = RequestMethod.GET)
    public ExecuteResult<KoclaOrderInfo> updateDegreeInfoByOrderInfo(@RequestParam("orderId") int orderId, @RequestParam("studentId") int studentId) {
        ExecuteResult<KoclaOrderInfo> executeResult = null;
        try {

            return executeResult;
        } catch (Exception e) {
            logger.error("updateDegreeInfoByOrderInfo is error:" + e);
            executeResult = new ExecuteResult<KoclaOrderInfo>(ResultCode.ERROR,null);
            executeResult.setMessage(e.getMessage() == null ? "内部报错" : e.getMessage());
            return executeResult;
        }
    }

    @RequestMapping(value = "updateOrderStatus", method = RequestMethod.GET)
    public ExecuteResult<KoclaOrderInfo> updateOrderStatus(@RequestParam("orderId") int orderId,@RequestParam("statusId") int statusId) {
        ExecuteResult<KoclaOrderInfo> executeResult = null;

        try {

            return executeResult;
        } catch (Exception e) {
            logger.error("updateOrderStatus is error:" + e);
            executeResult = new ExecuteResult<KoclaOrderInfo>(ResultCode.ERROR,null);
            executeResult.setMessage(e.getMessage() == null ? "内部报错" : e.getMessage());
            return executeResult;
        }
    }
}
