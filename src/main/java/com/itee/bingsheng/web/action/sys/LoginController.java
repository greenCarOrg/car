package com.itee.bingsheng.web.action.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itee.bingsheng.entity.SysFunction;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.service.ISysFuncService;
import com.itee.bingsheng.service.ISysUserService;
import com.itee.bingsheng.utils.Encrypt;
import com.itee.bingsheng.utils.MD5Util;
import com.itee.bingsheng.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Resource
    ISysUserService sysUserService;

    @Resource
    ISysFuncService sysFuncService;


    @RequestMapping("index")
    public String index(ModelMap modelMap, HttpSession session, HttpServletRequest request) {
        String keyCode = "bingsheng";
        String code = "-173a866122e4001a";
        String managerName = "admin";
        try {
            keyCode = Encrypt.decrypt(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keyCode.equals("bingsheng")) {
            session.setAttribute(WebConstants.LOGIN_WEB_SESSION, managerName);
            return "redirect:console/dashboard.do";
        } else {
            return "redirect:common/error_500.do";
        }
    }

    @RequestMapping("login")
    public String login(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        request.getSession().setAttribute("tempContextUrl",tempContextUrl);
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "syslogin", method = RequestMethod.POST)
    public Object syslogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,HttpServletRequest request, HttpServletResponse response) {
        JSONObject object = new JSONObject();
        SysUser SysUser = sysUserService.querySysUser(username);
        if(SysUser != null){
            if(MD5Util.ecodeByMD5AndSalt(password).equals(SysUser.getPassword())){
                String sysFunPath= sysFuncService.querySysFunctionForLogin(SysUser.getRoleId());
                if(sysFunPath!=null && sysFunPath!=""){
                    object.put("status", "1");
                    object.put("message", sysFunPath);
                }else{
                    object.put("status", "0");
                    object.put("message", "console/dashboard.do?explain=dashboarddo");
                }
                Map<Object,Object> respMap = null;
                if(SysUser.getRoleId() != 1){
                    List<SysFunction> list = sysFuncService.querySysFunctionByRoleId(SysUser.getRoleId(),0);
                    if(list != null && list.size() > 0) {

                        respMap = new LinkedHashMap<>();
                        for (int a = 0; a < list.size(); a++) {
                            SysFunction sysParentFunction = list.get(a);
                            List<SysFunction> funList =sysFuncService.querySysFunctionByRoleId(SysUser.getRoleId(), sysParentFunction.getId());
                            respMap.put(sysParentFunction,funList);
                        }
                    }
                }else{
                    List<SysFunction> list = sysFuncService.querySysFunctionParentList();
                    if(list != null && list.size() > 0) {
                        respMap = new LinkedHashMap<>();
                        for (int a = 0; a < list.size(); a++) {
                            SysFunction sysParentFunction =list.get(a);
                            respMap.put(sysParentFunction,sysFuncService.querySysFunctionSonList(sysParentFunction.getId()));
                        }
                    }
                    object.put("status", "1");
                    object.put("message", "console/dashboard.do?explain=dashboarddo");
                }
                SysUser.setSysFunc(respMap);
                HttpSession session = request.getSession();
                session.setAttribute(WebConstants.LOGIN_WEB_SESSION,SysUser);
                String ip = request.getLocalAddr();
                sysUserService.updateSysUserLoginInfo(ip,SysUser.getId());

            }else{  // 密码错误
                object.put("status", "0");
                object.put("message", "登录账号或者密码错误");
                return JSON.toJSONString(object);
            }
        }else{  //  用户名错误
            object.put("status", "0");
            object.put("message", "登录账号或者密码错误");
            return JSON.toJSONString(object);
        }
        return JSON.toJSONString(object);
    }


    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(WebConstants.LOGIN_WEB_SESSION);
        return "redirect:login.do";
    }
}
