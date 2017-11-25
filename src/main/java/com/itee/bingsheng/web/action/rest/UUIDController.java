package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController("newUUIDController")
@RequestMapping(value = "/rest")
public class UUIDController {

    @RequestMapping(value = "UUID", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult createUUID() {
        ExecuteResult result = new ExecuteResult();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        result.setResult(uuid);
        return result;
    }

    @RequestMapping(value = "QINIUToken", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult createQINIUToken() {
        ExecuteResult result = new ExecuteResult();
        String token = QiNiuXiuXiuUtil.getUpToken();
        result.setResult(token);
        return result;
    }
}
