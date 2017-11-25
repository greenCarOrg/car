package com.itee.bingsheng.web.action.sys;


import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.entity.User;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IUserService;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping(value = "/user")
public class UsersController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Resource
    private IUserService userService;


    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String users() {
        return "user/users";
    }


    @RequestMapping(value = "pagedUsers", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage pagedUsers(@RequestBody PageSpecification<Map<String, Object>> pageRequest, HttpServletRequest request)throws Exception {
        MybatisPage<Map<String, Object>> pages = new MybatisPage<Map<String, Object>>();
        Map<String, Object> param = new HashMap<>();
        param.put("phone", pageRequest.getData().get("phone"));
        pages.setTotalElements(userService.getAllUserCount(param));
        param.put("pageSize", pageRequest.getPageSize());
        param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
        pages.setContent(userService.queryAllUser(param));
        return pages;
    }





    @RequestMapping(value = "toAddUser", method = RequestMethod.GET)
    public String toAddUser(@ModelAttribute("user") User user, ModelMap modelMap) {
        return "user/addUser";
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap modelMap, Locale locale,HttpServletRequest request) throws JSONException {
        //获取登录人
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        Pattern pat = Pattern.compile("[\\da-zA-Z]{8,20}");
        Pattern patno = Pattern.compile(".*\\d.*");
        Pattern paten = Pattern.compile(".*[a-zA-Z].*");
        String str = user.getPassword();
        Matcher mat = pat.matcher(str);
        Matcher matno = patno.matcher(str);
        Matcher maten = paten.matcher(str);
        if (matno.matches() && maten.matches() && mat.matches()) {
        } else {

            return toAddUser(user, modelMap);
        }
        if (result.hasErrors()) {
            return toAddUser(user, modelMap);
        }
        return "redirect:../common/success.do?reurl=user/userSchoolRoles.do?userId="+ user.getUserId();
    }







    @RequestMapping(value = "removeUser", method = RequestMethod.GET)
    @ResponseBody
    public int removeUser(@RequestParam("userId") int userId) {
        try {
           // securityService.removeUserById(userId);
        } catch (Exception e) {
            logger.error("removeUser had error", e);
            return 0;
        }
        return userId;
    }



    public String toEditUser(ModelMap modelMap) {
        //初始化页面控件数据
        return "user/editUser";
    }

    @RequestMapping(value = "toUpdateUser", method = RequestMethod.GET)
    public String toUpdateUser(@ModelAttribute("user") User user, ModelMap modelMap) {
        return "user/editUser";
    }


    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user") User user, ModelMap modelMap, BindingResult result, Locale locale) throws Exception{

        Map<String, String> params = new HashMap<String, String>();

        User user1 = userService.updateUser(user);
        if (user1 == null) {
            return toEditUser(modelMap);
        }

        return "redirect:../common/success.do?reurl=user/users.do";
    }



}
