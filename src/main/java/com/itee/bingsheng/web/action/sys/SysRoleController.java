package com.itee.bingsheng.web.action.sys;

import com.itee.bingsheng.entity.SysFunction;
import com.itee.bingsheng.entity.SysRole;
import com.itee.bingsheng.entity.SysRoleFunc;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ISysFuncService;
import com.itee.bingsheng.service.ISysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "/system")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysFuncService sysFuncService;

    @RequestMapping(value = "sysrole", method = RequestMethod.GET)
    public String sysrole() {
        return "sysrole/list";
    }


    @RequestMapping(value = "toAddSysRole", method = RequestMethod.GET)
    public String toAddSysRole(@ModelAttribute("sysRole") SysRole sysRole, ModelMap modelMap,HttpServletRequest request) {
        List<SysFunction> list = sysFuncService.querySysFunctionParentList();
        Map<Object,Object> respMap = null;
        if(list != null && list.size() > 0) {
            respMap = new LinkedHashMap<>();
            for (int a = 0; a < list.size(); a++) {
                SysFunction sysParentFunction = (SysFunction)list.get(a);
                respMap.put(sysParentFunction,sysFuncService.querySysFunctionList(sysParentFunction.getId(),null));
            }
        }

        List<SysRole> roleList = sysRoleService.queryAllSysRole();
        request.setAttribute("respMap",respMap);
        request.setAttribute("roleList",roleList);
        return "sysrole/addSysRole";
    }

    @RequestMapping(value = "addSysRole", method = RequestMethod.POST)
    public String addSysRole(@ModelAttribute("sysRole") SysRole sysRole, BindingResult result, ModelMap modelMap, Locale locale, HttpServletRequest request) {
        if (result.hasErrors()) {
            return toAddSysRole(sysRole, modelMap, request);
        }
        try{
            int id = sysRoleService.saveSysRole(sysRole,request);
            if(id ==0){
                return toAddSysRole(sysRole, modelMap, request);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:../common/success.do?reurl=system/sysrole.do?explain=sysroledo";
    }

    @RequestMapping(value = "sysRolelist", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<SysRole> sysRolelist(@RequestBody PageSpecification<SysRole> pageRequest, ModelMap modelMap) {
        MybatisPage<SysRole> pages = new MybatisPage<SysRole>();
        try{
            List<SysRole> list = sysRoleService.queryPageAllSysRole(pageRequest.getPage(), pageRequest.getPageSize());
            pages.setContent(list);
            pages.setTotalElements(sysRoleService.queryPageAllSysRoleCount());
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

    @RequestMapping(value = "updateSysRoleFunc", method = RequestMethod.POST)
    public String updateSysRoleFunc(@ModelAttribute("sysRole") SysRole sysRole, BindingResult result, ModelMap modelMap, Locale locale, HttpServletRequest request) {
        try{
            sysRoleService.updateSysRoleFunc(request);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:../common/success.do?reurl=system/sysrole.do?explain=sysroledo";
    }

    @RequestMapping(value = "enabledSysRole")
    @ResponseBody
    public int enabledSysRole(@RequestParam("id") int id, @RequestParam("enabled") boolean enabled,HttpServletRequest request) {
        sysRoleService.updateSysRoleStatus(id,enabled,request);
        return id;
    }
}
