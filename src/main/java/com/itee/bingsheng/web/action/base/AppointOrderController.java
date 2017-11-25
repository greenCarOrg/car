package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.GoodGroup;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IAppointOrderService;
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
@RequestMapping(value = "/appointOrder")
public class AppointOrderController {

    @Resource
    private IAppointOrderService service;



    @RequestMapping(value = "appointOrder", method = RequestMethod.GET)
    public String appointOrder() {
        return "info/appointorder";
    }


    @RequestMapping(value = "appointOrderList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> appointOrderList(@RequestBody PageSpecification<GoodGroup> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            param.put("phone",pageRequest.getData().get("phone"));
            param.put("name",pageRequest.getData().get("name"));
            param.put("state",pageRequest.getData().get("state"));
            List<Map<String,Object>> list  = service.getAppointOrderList(param);
            pages.setContent(list);
            pages.setTotalElements(service.getAppointOrderCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

}
