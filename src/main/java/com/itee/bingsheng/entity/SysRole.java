package com.itee.bingsheng.entity;

import com.itee.bingsheng.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class SysRole implements java.io.Serializable{

    private int id;

    private String roleName;

    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String lastOperator;

    private String createTimeStr;  // 创建时间

    private String strStatus;   // 状态

    private String updateTimeStr;  // 更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getUpdateTimeStr() {
        if(updateTime != null){
            return DateUtils.dateToString(updateTime);
        }
        return "--";
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getCreateTimeStr() {
        if(createTime != null){
            return DateUtils.dateToString(createTime);
        }
        return "--";

    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getStrStatus() {
        return status == 1?"Yes":"No";
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }
}
