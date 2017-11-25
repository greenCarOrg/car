package com.itee.bingsheng.utils.httpjson;

import java.util.List;

/**
 * 批量注册软酷网json数据
 * Created by Administrator on 2017/5/11.
 */
public class ReqUserBatchRegisterParam {
    private String fromSystem;
    private List<UserJson> users;

    public static class UserJson {
        private String password;
        private String phone;


        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }

    public List<UserJson> getUsers() {
        return users;
    }

    public void setUsers(List<UserJson> users) {
        this.users = users;
    }
}
