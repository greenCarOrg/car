package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController("aController")
@RequestMapping(value = "/rest")
public class AdvertisementController extends BaseController {



    /**
     * type  0是顶部,1厂家详情页面广告
     *  limit  #{page},#{pageSize}

     * @return
     */
    @RequestMapping(value = "getAd", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getAd(@RequestParam("type") int type) {
        ExecuteResult<Map<String,Object>> result  = new ExecuteResult<>();
        try {
            Map<String,Object> map=new HashMap();
            map.put("type",type);

            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(map);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }




}
