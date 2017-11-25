package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "recommend")
public class Recommend implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int sex;         //0保密  1男  2女
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //最后登录时间
    private String phone;  //电话号码
    private String address; //第三方标识
    private String name;
    private String head;    //头像
    private int state;         //1是否被锁定 0正常
    private int createor;
    private String idCard;
    private String remark;

    public Recommend() {
    }

    public int getCreateor() {
        return createor;
    }

    public void setCreateor(int createor) {
        this.createor = createor;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, precision = 20, scale = 0)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
