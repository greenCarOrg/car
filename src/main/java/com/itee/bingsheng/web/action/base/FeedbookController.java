package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IFeedbookService;
import com.itee.bingsheng.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户反馈
 */
@Controller
@RequestMapping(value = "/feedbook")
public class FeedbookController {

    @Resource
    private IFeedbookService service;



    @RequestMapping(value = "feedbook", method = RequestMethod.GET)
    public String code() {
        return "feedbook/feedbook";
    }


    @RequestMapping(value = "feedbookList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> feedbookList(@RequestBody PageSpecification<Object> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            List<Map<String,Object>> list  = service.getFeedbookList(param);
            pages.setContent(list);
            pages.setTotalElements(service.getFeedbookCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }


    @RequestMapping(value = "toReplay", method = RequestMethod.GET)
    private String toReplay(@RequestParam("id") int id,ModelMap modelMap)throws Exception {
        Map<String ,Object> map=new HashMap<>();
        map.put("id",id);
        List<Map<String,Object>> list  = service.getFeedbookList(map);
        modelMap.addAttribute("data",list.get(0));
        return "feedbook/replay";
    }



    @RequestMapping(value = "updateFeedbook", method = RequestMethod.POST)
    public String updateFeedbook(ModelMap modelMap,HttpServletRequest request)throws Exception {
        Map<String,Object> map=new HashMap<>();
        map.put("id",request.getParameter("id"));
        try {
            String replay=request.getParameter("replay");
            HttpSession session = request.getSession();
            SysUser sysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
            map.put("state",1);
            map.put("createor",sysUser.getId());
            map.put("replay",replay);
            service.updateFeedbook(map);
        }catch (Exception e) {
            e.printStackTrace();
            List<Map<String,Object>> list  = service.getFeedbookList(map);
            modelMap.addAttribute("data",list.get(0));
            return "feedbook/replay";
        }
        return "feedbook/feedbook";
    }
}
