package com.itee.bingsheng.pay.base.bean;

import com.itee.bingsheng.entity.User;

/**
 * Created by abc on 2016/2/29.
 */
public class OrderBean {

    private User user;              // 操作人
    private int payType;         // 支付方式 1.现金 2.微信 3.支付宝
    private int chargeType;         //学生充值类型
    private String tradeType;       // 业务类型 1. 充值ֵ
    private Integer orderKey;       // 订单主键
    private String orderId;         // 订单编号
    private Double amount;          // 金额
    private String spbillCreateIp; // 客户端IP
    private String remark;          // 订单描述
    private String codeUrl;         // 二维码地址
    private String subject;         // 订单标题
    private String notifyUrl;       // 订单异步消息接收地址

    public OrderBean() {
    }

    public Integer getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(Integer orderKey) {
        this.orderKey = orderKey;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getChargeType() {
        return chargeType;
    }

    public void setChargeType(int chargeType) {
        this.chargeType = chargeType;
    }
}
