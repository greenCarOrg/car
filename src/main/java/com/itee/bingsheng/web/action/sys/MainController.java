package com.itee.bingsheng.web.action.sys;

import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/console")
public class MainController {



    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String dashboard(ModelMap modelMap, HttpServletRequest request) {
        return "layouts/dashboard";
    }


    @RequestMapping(value = "adList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> adList(@RequestBody PageSpecification<Object> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            pages.setContent(null);
            pages.setTotalElements(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

}
