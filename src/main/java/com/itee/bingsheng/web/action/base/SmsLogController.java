package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IComboboxService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/smsLog")
public class SmsLogController {

    @Resource
    private IComboboxService service;



    @RequestMapping(value = "smsLog", method = RequestMethod.GET)
    public String smsLog() {
        return "info/smsLog";
    }


    @RequestMapping(value = "smsLogList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> smsLogList(@RequestBody PageSpecification<Object> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("phone_number",pageRequest.getData().get("phone_number"));
            pages.setTotalElements(service.getSmsLogCount(param));
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            List<Map<String,Object>> list  = service.getSmsLogList(param);
            pages.setContent(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }
}