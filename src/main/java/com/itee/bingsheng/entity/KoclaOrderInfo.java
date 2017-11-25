package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 网订单
 * Created by abc on 2015/12/12.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "kocla_order_info")
public class KoclaOrderInfo {

    private int orderId;                                // 主键ID
    private String orderCode;                           // 订单号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;                             // 订单时间
    private String koclaAccount;                        // 网账号
    private String mobileIphone;                        // 手机号
    private boolean isPay;                              // 是否支付
    private boolean isChecked;                         // 是否已校验
    private int statusId;                               // 订单状态
    private int validateCode;                          // 订单密码
    private String remark ;                             // 备注


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, precision = 12, scale = 0)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


    public Date getOrderTime() {
        return orderTime;
    }


    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getKoclaAccount() {
        return koclaAccount;
    }

    public void setKoclaAccount(String koclaAccount) {
        this.koclaAccount = koclaAccount;
    }

    public String getMobileIphone() {
        return mobileIphone;
    }

    public void setMobileIphone(String mobileIphone) {
        this.mobileIphone = mobileIphone;
    }

    public boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }


    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(int validateCode) {
        this.validateCode = validateCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public KoclaOrderInfo() {super();    }

    public KoclaOrderInfo(int orderId, String orderCode, Date orderTime, String koclaAccount, String mobileIphone, boolean isPay, boolean isChecked, int statusId, int validateCode, String remark) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.orderTime = orderTime;
        this.koclaAccount = koclaAccount;
        this.mobileIphone = mobileIphone;
        this.isPay = isPay;
        this.isChecked = isChecked;
        this.statusId = statusId;
        this.validateCode = validateCode;
        this.remark = remark;

    }


}
