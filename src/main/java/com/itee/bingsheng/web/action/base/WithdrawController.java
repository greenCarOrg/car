package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IWithdrawService;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/withdraw")
public class WithdrawController extends BaseController{

    @Resource
    private IWithdrawService service;


    @RequestMapping(value = "withdraw", method = RequestMethod.GET)
    public String withdraw() {
        return "info/withdraw";
    }


    @RequestMapping(value = "withdrawList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> withdrawList(@RequestBody PageSpecification<Object> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            String state=pageRequest.getData().get("state").toString();
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(state)){
                param.put("state",Integer.parseInt(state));
            }
            String type=pageRequest.getData().get("type").toString();
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(type)){
                param.put("type",Integer.parseInt(type));
            }
            List<Map<String,Object>> list = service.queryAllWithdraw(param);
            pages.setContent(list);
            pages.setTotalElements(service.getAllCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }





    @RequestMapping(value = "enabledState")
    @ResponseBody
    public int enabledState(@RequestParam("id") int id, @RequestParam("state") int state, HttpServletRequest request)throws Exception {
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("state",state);
        map.put("operator",SysUser.getId());
        return service.updateWithDraw(map);
    }



}
