package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.entity.User;
import com.itee.bingsheng.service.IComboboxService;
import com.itee.bingsheng.service.IUserService;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.utils.CreateUUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController("newUserController")
@RequestMapping(value = "/rest")
public class UserController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private ServletContext servletContext;

    @Resource
    private IUserService userService;
    @Resource
    private IComboboxService comboboxService;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }



    /**
     * 前端注册用户
     * phone    手机号码
     * referphone     推荐人
     * password       密码
     * 生成UUID作为用户标识,前端请求通过UUID到sessiong中查找用户
     */
    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    public ExecuteResult registerUser(@RequestBody Map<String,Object> param, HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            User user=userService.getUserByPhone(param.get("phone").toString());
            if(user==null){
                user=userService.registerUser(param,request);
                if(user!=null && user.getUserId()>0){
                    result.setResultCode(ResultCode.SUCCESS);
                    result.setResult(user);
                }else {
                    result.setResultCode(ResultCode.FAIL);
                }
            }else{
                result.setResultCode(ResultCode.FAIL);
                result.setMessage("你已经注册为平台用户,可以直接登录或是找回密码");
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }









    /**
     * 用户登录
     * @param phone
     * @param password
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="userLogin",method = RequestMethod.GET)
    public  ExecuteResult  userLogin(@RequestParam("phone") String phone, @RequestParam("password") String password,HttpServletRequest request) throws Exception{
        ExecuteResult result=new ExecuteResult();
        User user=userService.userLogin(phone,password);
        if(user!=null){
            String uuid= CreateUUIDUtil.createUUID();
            user.setUuid(uuid);
            HttpSession session = request.getSession();
            session.setAttribute(uuid,user);
            result.setResult(user);
            result.setResultCode(ResultCode.SUCCESS);
        }else{
            result.setResultCode(ResultCode.FAIL);
            result.setMessage("用户名和密码错误");
        }
        return  result;
    }


    /**
     * 获取用户优惠券信息
     * uuid  用户id
     * page 当前页面
     * pageSize 每页显示多少
     */
    @RequestMapping(value = "getUserCoupon", method = RequestMethod.GET)
    public ExecuteResult getUserCoupon(@RequestParam("uuid") String uuid, @RequestParam("page") int page,@RequestParam("pageSize") int pageSize,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            HttpSession session = request.getSession();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else{
                Map<String,Object> param=new HashMap<>();
                param.put("uuid",uuid);
                param.put("page",page);
                param.put("pageSize",pageSize);
                param.put("userId",user.getUserId());

                Map<String,Object> map=new HashMap<>();
                param.put("state",0);
                map.put("anused",userService.getUserCoupon(param));
                param.put("state",1);
                map.put("used",userService.getUserCoupon(param));
                param.put("state",2);
                map.put("overDate",userService.getUserCoupon(param));
                result.setResult(map);
                result.setResultCode(ResultCode.SUCCESS);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }


    /**
     * 用户签到赠送积分
     * uuid  用户id
     */
    @RequestMapping(value = "giftUserPoint", method = RequestMethod.POST)
    public ExecuteResult giftUserPoint(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else{
                //先获取签到赠送积分额度
                Double point=comboboxService.getCodeValueByCode("SIGNPOINT");
                //变更用户表里面的总积分
                //插入记录
                param.put("userId",user.getUserId());
                param.put("point",point);
                param.put("type",1);
                param.put("current_point",0);
                param.put("remark","手机端签到赠送积分");
                int flag=userService.giftUserPoint(param);
                if(flag>0){
                    result.setResultCode(ResultCode.SUCCESS);
                }else{
                    result.setResultCode(ResultCode.FAIL);
                }
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }


    /**
     * 修改用户信息
     * 密码,支付密码或是登录密码
     * uuid
     * password
     * paypwd
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public ExecuteResult updateUserPassword(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId", user.getUserId());
                int flag=userService.updatePassword(param);
                result.setResultCode(flag>0?ResultCode.SUCCESS:ResultCode.FAIL);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }







    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public ExecuteResult<User> updateUser(@RequestBody User user) {
        ExecuteResult<User> userExecuteResult = new ExecuteResult<>();
        User user1 = null;
        try {
            return new ExecuteResult<>(ResultCode.SUCCESS);


        } catch (Exception e) {

        }
        return new ExecuteResult<>(ResultCode.FAIL, user1);
    }



    /**
     * 更换用户图像
     *
     * @param userId
     * @param file
     * @return
     */
    @RequestMapping(value = "changeUserFace", method = RequestMethod.POST)
    public ExecuteResult changeUserFace(@RequestParam("userId") int userId, @RequestParam("file") CommonsMultipartFile file) {
        ExecuteResult result = new ExecuteResult();
        try {

            result.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error("changeUserFace had error!", e);
            result.setResultCode(ResultCode.FAIL);
        } finally {
            return result;
        }
    }





    /**
     * App更新密码
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "appUpdateUserPassword", method = RequestMethod.GET)
    public ExecuteResult appUpdateUserPassword(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        ExecuteResult result = new ExecuteResult<>();
        try {

            result.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error("appUpdateUserPassword had error!", e);
            result.setResultCode(ResultCode.FAIL);
        } finally {
            return result;
        }
    }

}
