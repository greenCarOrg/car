package com.itee.bingsheng.entity;

import com.itee.bingsheng.utils.DateUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_user")
public class SysUser implements java.io.Serializable  {


    private int id;

    private String userName;

    private String password;

    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String lastOperator;

    private int roleId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private String lastLoginIp;

    private String createTimeStr;

    private String lastLoginTimeStr;

    private String roleName;


    private String statusStr;

    private String realName;

    private String phone;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, precision = 12, scale = 0)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    private Map<Object,Object> sysFunc = new HashMap<>();


    public String getStatusStr() {
        return status == 1 ? "可用":"停用";
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getCreateTimeStr() {
        if(createTime != null){
            return DateUtils.dateToString(createTime);
        }
        return "--";
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getLastLoginTimeStr() {
        if(lastLoginTime != null){
            return DateUtils.dateToString(lastLoginTime);
        }
        return "--";
    }

    public void setLastLoginTimeStr(String lastLoginTimeStr) {
        this.lastLoginTimeStr = lastLoginTimeStr;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Transient
    public Map<Object, Object> getSysFunc() {
        return sysFunc;
    }

    public void setSysFunc(Map<Object, Object> sysFunc) {
        this.sysFunc = sysFunc;
    }


}
