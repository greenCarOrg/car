package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.entity.User;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.service.ICommonService;
import com.itee.bingsheng.service.IFeedbookService;
import com.itee.bingsheng.service.ICommentService;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import com.itee.bingsheng.utils.RandomUtil;
import com.itee.bingsheng.utils.SendSMS;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@RestController("cController")
@RequestMapping(value = "/rest")
public class CommonController extends BaseController {


    protected final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());

    @Resource
    ICommonService commonService;
    @Resource
	ICommentService commentService;
    @Resource
    IFeedbookService feedbookService;
    /**
     *int   type==1
     * 模版CODE::   SMS_97860001
     * 您正在登录互利商城，您的登录验证码为：${code}，该验证码 5 分钟内有效，请勿泄漏于他人
     * type==2
     * 模版CODE::   SMS_97575016
     * 您正在注册互利商城，您的验证码为：${code}，该验证码 5 分钟内有效，请勿泄漏于他人
     *String phoneNumber
     * @param request
     * @return
     */
    @RequestMapping(value = "getVerifyCode", method = RequestMethod.GET)
    public ExecuteResult getVerifyCode(@RequestParam("phone") String phone,@RequestParam("type") int type,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            String rand= RandomUtil.getSixNumber();
            String returnmsg= SendSMS.sendSms(phone,type,rand);
            commonService.saveSmsLog(phone,rand,returnmsg,type);
            if(("请求成功").equals(returnmsg)){
                result.setResultCode(ResultCode.SUCCESS);
                result.setMessage(rand);
            }else {
                result.setResultCode(ResultCode.FAIL);
                result.setMessage(returnmsg);
            }
            logger.info("手机号码为"+phone+"的用户获取验证码   "+rand+"返回状态"+returnmsg);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }


    /**
     * 获取省市信息
     * level 1 一级城市
     *      2 二级县
     *      3 三级城市
     *      4 区县
     * parent_id 上一父id
     */
    @RequestMapping(value = "getRegion", method = RequestMethod.GET)
    public ExecuteResult getRegion(@RequestParam("level") int level,@RequestParam("parentId") int parentId)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        Map<String,Object> map=new HashMap<>();
        map.put("level",level);
        if(parentId>0){
            map.put("parentId",parentId);
        }
        try {
            List<Map<String,Object>> list=commonService.getRegion(map);
            result.setResult(list);
            result.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 首页问题反馈
     * uuid   用户编号
     * content  反馈内容
     * img      七牛云提交的图片返回的连接
     * name     联系人姓名
     * phone    联系人电话
     */
    @RequestMapping(value = "submitFeedbook", method = RequestMethod.POST)
    public ExecuteResult submitFeedbook(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId",user.getUserId());
                int flag = feedbookService.submitFeedbook(param);
                if (flag > 0) {
                    result.setResultCode(ResultCode.SUCCESS);
                } else {
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
     * 获取用户常用收获地址
     * uuid
     */
    @RequestMapping(value = "getUserAddress", method = RequestMethod.GET)
    public ExecuteResult getUserAddress(@RequestParam("uuid") String uuid,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            HttpSession session = request.getSession();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                List<Map<String, Object>> list = commonService.getUserAddress(user.getUserId());
                result.setResult(list);
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
     * 设置用户默认收货地址
     * uuid
     * addressId
     */
    @RequestMapping(value = "updateUserDefaultAddress", method = RequestMethod.POST)
    public ExecuteResult updateUserDefaultAddress(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId",user.getUserId());
                int flag=commonService.updateUserDefaultAddress(param);
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
     * 用户新增收货地址
     * uuid  用户id
     * consignee  收货人
     * province 省份
     * city 城市
     * district 地区
     * twon  乡镇
     * address 地址
     * postcode 邮政编码
     * mobile 手机
     * is_default 1默认收货地址,0常用地址
     */
    @RequestMapping(value = "addUserAddress", method = RequestMethod.POST)
    public ExecuteResult addUserAddress(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId", user.getUserId());
                int flag = commonService.addUserAddress(param);
                result.setResultCode(flag > 0 ? ResultCode.SUCCESS : ResultCode.FAIL);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }


    /**
     * 获取七牛云的令牌
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getQNToken",method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getQNToken(ModelMap modelMap) throws Exception{
        ExecuteResult result=new ExecuteResult();
        result.setResult(QiNiuXiuXiuUtil.getUpToken());
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

}
