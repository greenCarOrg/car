package com.itee.bingsheng.pay.koclawallet.service.impl;

import com.itee.bingsheng.pay.base.bean.CodeUrlBean;
import com.itee.bingsheng.pay.base.bean.OrderBean;
import com.itee.bingsheng.pay.base.enums.ReturnCode;
import com.itee.bingsheng.pay.base.utils.EncryptUtils;
import com.itee.bingsheng.pay.koclawallet.service.KoclawalletService;
import com.itee.bingsheng.pay.weixin.bean.NotifyReturnBean;
import com.itee.bingsheng.utils.TKFile;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class KoclawalletServiceImpl implements KoclawalletService {



    private static final String key = "$yAUIF4CUAX4kAhOC=";



    public CodeUrlBean getCodeUrl(OrderBean orderBean) throws Exception {

        String corpCode = TKFile.getPropValue("application.properties", "corp_code");

        CodeUrlBean codeUrlBean = new CodeUrlBean();
        JSONObject json = new JSONObject();
        StringBuffer strBuff = new StringBuffer();
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

//        String subject = "学员[" + orderBean.getStudent().getStudentName() + "]充值";
//        json.put("studentId", orderBean.getStudent().getStudentId());
//        json.put("schoolId", orderBean.getStudent().getSchool().getSchoolId());
//        json.put("corpCode", corpCode);
//        //json.put("guardAccount",null);
//        json.put("money", df.format(orderBean.getAmount()));
//        json.put("subject", subject);
//        json.put("chargeType", orderBean.getChargeType());
//
//        strBuff.append(orderBean.getStudent().getStudentId());
//        strBuff.append(orderBean.getStudent().getSchool().getSchoolId());
        strBuff.append(corpCode);
        //strBuff.append("");
        strBuff.append(orderBean.getChargeType());
        strBuff.append(df.format(orderBean.getAmount()));
        strBuff.append(key);
        String str = strBuff.toString();
        json.put("sign", EncryptUtils.md5(strBuff.toString()).toLowerCase());
        String jsonstr = json.toString().replace("\"", "\'");
        codeUrlBean.setCodeUrl(jsonstr);
        codeUrlBean.setReturnCode("001");
        codeUrlBean.setReturnMsg("钱包充值");

        return codeUrlBean;
    }

    /**
     * 支付接口异步消息处理
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    public Object handleNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {


        JSONObject json = new JSONObject();

        String studentId = request.getParameter("studentId");
        String chargeNo = request.getParameter("chargeNo");
        String money = request.getParameter("money");
        String chargeType = request.getParameter("chargeType");

/*        Student student = studentService.getStudenByStudentId(Integer.parseInt(studentId));

        if (student != null) {
            if (student.getIsReport()) {
                ChargeRecord chargeRecord = new ChargeRecord();
                chargeRecord.setStudent(student);
                chargeRecord.setInputId(100001);
                chargeRecord.setInputName("钱包");
                chargeRecord.setMoney(Double.valueOf(money));
                chargeRecord.setType(1);    *//*1代表充值，2代表退费，3代表报班，4代表续班*//*
                chargeRecord.setPayType(5);
                chargeRecord.setChargeType(Integer.parseInt(chargeType));

                chargeRecord.setRemark("");
                chargeRecord.setChargeState(0);
                chargeRecord.setUuid(CreateUUIDUtil.createUUID());
                chargeRecord = teachService.addChargeRecord(chargeRecord);
                teachService.addCharge(chargeRecord);
//                List<StudentClass> studentClassList = teachService.handleChargeBusiness(chargeRecord);
//                for(StudentClass bean:studentClassList)
//                {
//                    studentReportService.createStudentCurriculumInfo(bean);
//                }
                //更新学生状态并推送给中心库
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                json.put("code", 0);
                json.put("msg", "充值成功");
            } else {
                json.put("code", 1);
                json.put("msg", "此学员未建立学籍");
            }

        } else {
            json.put("code", 2);
            json.put("msg", "找不到此学员");
        }*/
        return json;
    }

    /**
     * 保存订单信息
     *
     * @param returnBean
     * @return
     * @throws Exception
     */
    private Map<String, String> saveOrderInfo(NotifyReturnBean returnBean) throws Exception {
        Map<String, String> resonseMap = new HashMap<>();
        String returnMsg = "OK";
        String returnCode = ReturnCode.FAIL;
        // 根据返回的商户订单号，获取订单信息
/*        ErpOrderInfo erpOrderInfo = erpOrderInfoService.getOrderInfoByOrderId(returnBean.getOutTradeNo());
        if (null != erpOrderInfo) {
            *//* 支付成功 *//*
            switch (returnBean.getResultCode()) {
                case ReturnCode.SUCCESS:
                    returnCode = ReturnCode.SUCCESS;
                    if (ErpOrderState.SUCCESS.getStateId() != erpOrderInfo.getStateId()) {
                        erpOrderInfo.setStateId(ErpOrderState.SUCCESS.getStateId());
                        erpOrderInfo.setOrderCode(returnBean.getTransactionId());
                        erpOrderInfo.setPayAccount(returnBean.getOpenId());
                    }
                    break;
                case ReturnCode.FAIL:
                    erpOrderInfo.setStateId(ErpOrderState.CLOSE.getStateId());
                    erpOrderInfo.setRemark(returnBean.getReturnMsg());
                    break;
            }
        } else {
            returnMsg = "未查询到商户对应的订单";
        }
        erpOrderInfo.setUpdateDate(new Date());
        erpOrderInfo.setRemark("支付宝支付结果通知：" + returnBean.getReturnMsg());
        erpOrderInfo = erpOrderInfoService.saveOrderResult(erpOrderInfo);*/
        // 返回给微信的结果集
        resonseMap.put(ReturnCode.RETURN_CODE, returnCode);
        resonseMap.put(ReturnCode.RETURN_CODE, returnMsg);
        return resonseMap;
    }
}