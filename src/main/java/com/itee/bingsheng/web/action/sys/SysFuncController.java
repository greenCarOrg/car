package com.itee.bingsheng.web.action.sys;

import com.itee.bingsheng.entity.SysFunction;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ISysFuncService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping(value = "/system")
public class SysFuncController {

    @Resource
    private ISysFuncService sysFuncService;

    @RequestMapping(value = "left", method = RequestMethod.GET)
    public String left() {
        return "layouts/left";
    }

    @RequestMapping(value = "sysfunc", method = RequestMethod.GET)
    public String corporation() {
        return "sysfunc/list";
    }


    @RequestMapping(value = "toAddSysFunc", method = RequestMethod.GET)
    public String toAddSysFunc(@ModelAttribute("sysFunction") SysFunction sysFunction, ModelMap modelMap,HttpServletRequest request) {
        List<SysFunction> list = sysFuncService.querySysFunctionParentList();
        request.setAttribute("list",list);
        return "sysfunc/addSysFunc";
    }

    @RequestMapping(value = "addSysFunc", method = RequestMethod.POST)
    public String addSysFunc(@ModelAttribute("sysFunction") SysFunction sysFunction, BindingResult result, ModelMap modelMap, Locale locale, HttpServletRequest request) {
        if (result.hasErrors()) {
            return toAddSysFunc(sysFunction, modelMap, request);
        }
        try{
            String isparent = (String)request.getParameter("isparent");
            if(isparent != null && Integer.valueOf(isparent).intValue() == 0){
                String parentid = (String)request.getParameter("parentid");
                sysFunction.setParentId(Integer.valueOf(parentid));
            }else{
                sysFunction.setParentId(0);
            }
            int id = sysFuncService.saveSysFunction(sysFunction,request);
            if(id ==0){
                return "redirect:../common/failure.do?reurl=system/toAddSysFunc.do?explain=addsysfuncdo";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:../common/success.do?reurl=system/allSysFunc.do?explain=addsysfuncdo";
    }

    @RequestMapping(value = "allSysFunc", method = RequestMethod.GET)
    public String allSysFunc() {
        return "sysfunc/list";
    }

    @RequestMapping(value = "sysFunclist", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<SysFunction> sysFunclist(@RequestBody PageSpecification<SysFunction> pageRequest, ModelMap modelMap) {
        MybatisPage<SysFunction> pages = new MybatisPage<SysFunction>();
        try{
            List<SysFunction> list  = sysFuncService.querySysFunctionList(0,null,pageRequest.getPageSize(),pageRequest.getPage());
            pages.setContent(list);
            pages.setTotalElements(sysFuncService.querySysFunctionCount(0,null));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

    @RequestMapping(value = "enabledSysFunc")
    @ResponseBody
    public int enabledSysFunc(@RequestParam("id") int id, @RequestParam("enabled") boolean enabled,HttpServletRequest request) {
        sysFuncService.updateSysFuncStatus(id,enabled,request);
        return id;
    }
}
