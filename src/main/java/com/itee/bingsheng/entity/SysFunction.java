package com.itee.bingsheng.entity;



public class SysFunction implements java.io.Serializable{

    private int id;

    private String funcName;  // 功能名

    private int parentId;    //  父类ID

    private String funcPath;  //  功能路径

    private int status;     // 状态ID

    private String parentName;  //  父节点名称

    private String icon;

    private int orderNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFuncName() {
        return funcName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getFuncPath() {
        return funcPath;
    }

    public void setFuncPath(String funcPath) {
        this.funcPath = funcPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParentName() {
        return parentName == null || "".equals(parentName)? "--" : parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
