package com.itee.bingsheng.utils.httpjson;

/**
 * 请求失败默认返回
 * Created by Administrator on 2017/5/11.
 */
public class ErrorResp extends BaseResp {
    @Override
    public boolean getStatus() {
        return false;
    }
}
