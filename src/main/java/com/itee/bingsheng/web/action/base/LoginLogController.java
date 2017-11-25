package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.GoodGroup;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IComboboxService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/loginlog")
public class LoginLogController {

    @Resource
    private IComboboxService service;



    @RequestMapping(value = "loginlog", method = RequestMethod.GET)
    public String loginlog() {
        return "info/loginlog";
    }


    @RequestMapping(value = "loginLogList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> loginLogList(@RequestBody PageSpecification<GoodGroup> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            param.put("phone",pageRequest.getData().get("phone"));
            List<Map<String,Object>> list  = service.getLoginLogList(param);
            pages.setContent(list);
            pages.setTotalElements(service.getLoginLogCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

}
