package com.itee.bingsheng.web.action.sys;

import com.itee.bingsheng.entity.SysRole;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ISysRoleService;
import com.itee.bingsheng.service.ISysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping(value = "/system")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysRoleService sysRoleService;

    @RequestMapping(value = "sysuser", method = RequestMethod.GET)
    public String sysuser() {
        return "sysuser/list";
    }


    @RequestMapping(value = "toAddSysUser", method = RequestMethod.GET)
    public String toAddSysUser(@ModelAttribute("sysUser") SysUser sysUser, ModelMap modelMap,HttpServletRequest request) {
        List<SysRole> roleList = sysRoleService.queryAllSysRole();
        request.setAttribute("roleList",roleList);
        return "sysuser/addSysUser";
    }

    @RequestMapping(value = "toEditSysUserPage", method = RequestMethod.GET)
    public String toEditSysUserPage(@ModelAttribute("sysUser") SysUser sysUser, ModelMap modelMap,HttpServletRequest request){
        SysUser SysUser = sysUserService.querySysUser(sysUser.getUserName());
        modelMap.put("sysUser", SysUser);

        List<SysRole> roleList = sysRoleService.queryAllSysRole();
        request.setAttribute("roleList", roleList);

        return "sysuser/editSysUser";
    }


    @RequestMapping(value = "editSysUser", method = RequestMethod.POST)
    public String editSysUser(@ModelAttribute("sysUser") SysUser sysUser, BindingResult result, ModelMap modelMap,HttpServletRequest request) {
        if (result.hasErrors()) {
            return toAddSysUser(sysUser, modelMap, request);
        }
        try{
            int id = sysUserService.editSysUser(sysUser, request);
            if(id ==0){
                return toEditSysUserPage(sysUser, modelMap, request);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:../common/success.do?reurl=system/sysuser.do?explain=sysuserdo";
    }

    @RequestMapping(value = "addSysUser", method = RequestMethod.POST)
    public String addSysUser(@ModelAttribute("sysUser") SysUser sysUser, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
        if (result.hasErrors()) {
            return toAddSysUser(sysUser, modelMap, request);
        }
        try{
            int id = sysUserService.insertSysUser(sysUser,request);
            if(id ==0){
                return toAddSysUser(sysUser, modelMap, request);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:../common/success.do?reurl=system/sysuser.do?explain=sysuserdo";
    }

    @RequestMapping(value = "sysUserlist", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<SysUser> sysUserlist(@RequestBody PageSpecification<SysUser> pageRequest) {
        MybatisPage<SysUser> pages = new MybatisPage<SysUser>();
        try{
            List<SysUser> list = sysUserService.queryAllSysAdminUser(pageRequest.getPage(), pageRequest.getPageSize());
            pages.setContent(list);
            pages.setTotalElements(sysUserService.queryAllSysAdminUserCount());
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

    @RequestMapping(value = "enabledSysUser")
    @ResponseBody
    public int enabledSysUser(@RequestParam("id") int id, @RequestParam("enabled") boolean enabled,HttpServletRequest request) {
        sysUserService.updateSysUserStatus(id,enabled,request);
        return id;
    }
}
