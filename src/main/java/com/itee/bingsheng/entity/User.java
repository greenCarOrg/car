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
@Table(name = "user")
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int userId;
    private String password; //密码
    private String paypwd;   //支付密码
    private int sex;         //0保密  1男  2女
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectDate;   //预产期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date childBirth;   //宝宝生日
    private double userMoney;//用户金额
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regTime;     //注册时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin; //最后登录时间
    private String phone;  //电话号码
    private String oauth;  //第三方来源认证
    private String openid; //第三方标识
    private String userName;
    private String head;    //头像
    private int state;         //1是否被锁定 0正常
    private String token;   //用于app 授权类似于session_id
    private int focus;      //微信是否发滚珠     1关注 2取消关注 0未关注

    public User() {
    }
    private String uuid;   //前端用户标识

    public User(int userId) {
        this.userId = userId;
    }

    @Id
    @JoinColumn(name = "user_id")
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, precision = 20, scale = 0)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypwd() {
        return paypwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public double getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(double userMoney) {
        this.userMoney = userMoney;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    @Transient
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Date getChildBirth() {
        return childBirth;
    }

    public void setChildBirth(Date childBirth) {
        this.childBirth = childBirth;
    }
}
