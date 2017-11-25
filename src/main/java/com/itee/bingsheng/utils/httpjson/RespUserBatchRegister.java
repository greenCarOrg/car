package com.itee.bingsheng.utils.httpjson;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public class RespUserBatchRegister extends BaseResp {

    /**
     * 0:成功
     * 500:系统异常
     * -1:失败
     */
    private Integer result;

    private List<RespUser> value;

    @Override
    public boolean getStatus() {
        if (null != result && result.intValue() == 1) {
            return true;
        }
        return false;
    }

    public static class RespUser {
        public final static Integer RESULT_CODE_SUCCESS = 0;

        private String username;
        private String userId;
        private String password;
        private String realname;
        private String email;
        private String phone;
        private Integer resultCode;
        private String resultMsg;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Integer getResultCode() {
            return resultCode;
        }

        public void setResultCode(Integer resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            switch (resultCode) {
                case 1:
                    return "用户名、邮箱、手机号不能同时为空";
                case 2:
                    return "用户名长度不能小于4或大于40";
                case 3:
                    return "用户名不符合规则";
                case 4:
                    return "用户名含特殊字符";
                case 5:
                    return "用户名已经存在";
                case 10:
                    return "密码不能为空";
                case 11:
                    return "密码长度不能小于6或者大于12";
                case 12:
                    return "密码不能有中文";
                case 21:
                    return "姓名不能有为空";
                case 22:
                    return "姓名不能少于4个字符或者大于40个字符";
                case 31:
                    return "邮箱不合法";
                case 32:
                    return "邮箱已存在";
                case 33:
                    return "手机号不合法";
                case 34:
                    return "手机号已存在";
                default:
                    return "注册异常";
            }
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<RespUser> getValue() {
        return value;
    }

    public void setValue(List<RespUser> value) {
        this.value = value;
    }
}
