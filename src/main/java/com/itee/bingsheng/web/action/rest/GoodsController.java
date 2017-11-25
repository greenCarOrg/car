package com.itee.bingsheng.web.action.rest;
import java.util.HashMap;
import com.itee.bingsheng.entity.User;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.service.IGoodService;
import com.itee.bingsheng.service.ICollectionService;
import com.itee.bingsheng.service.ICommentService;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController("gcController")
@RequestMapping(value = "/rest")
public class GoodsController extends BaseController {

    protected final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());

    @Resource
	ICommentService commentService;

    @Resource
	ICollectionService collectionService;

    @Resource
    IGoodService goodService;



    /**
     * 商城列表根据商品分组展示商品信息
     * groupId 1 百分百商品   2百分比商品   3线下服务商品
     */
    @RequestMapping(value = "getGoodByGroup", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getGoodByGroup(@RequestParam("groupId") int groupId,HttpServletRequest request)throws Exception{
        ExecuteResult result  = new ExecuteResult();
        try {
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("cat_id",groupId);
            map.put("is_on_sale",1);
            map.put("data",goodService.queryGoodList(map));
            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(map);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }


    /**
     * 商城列表点击商品进入商品  (商品)
     * goodId
     */
    @RequestMapping(value = "getGoodByGoodId", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getGoodByGoodId(@RequestParam("goodId") int goodId,HttpServletRequest request)throws Exception{
        ExecuteResult result  = new ExecuteResult();
        try {
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("goods_id",goodId);
			map.put("state",1);
			map.put("data",goodService.queryGoodList(map));
            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(map);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }


    /**
     * 商城列表点击商品进入商品评论  (评论)
     * @param goodId
     * @param page
     * @param pageSize
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getGoodCommentByGoodId", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getGoodCommentByGoodId(@RequestParam("goodId") int goodId,@RequestParam("page") int page,@RequestParam("pageSize") int pageSize,HttpServletRequest request)throws Exception{
        ExecuteResult result  = new ExecuteResult();
        try {
            Map<String,Object> map=new HashMap<String, Object>();
			map.put("goods_id",goodId);
			map.put("page",page);
			map.put("pageSize",pageSize);
			map.put("state",1);
			map.put("data",commentService.queryAllComment(map));




            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(map);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 添加商品到购物车
     * #{goodId},
     * #{uuid}
     */
    @RequestMapping(value = "submitGoodToCart", method = RequestMethod.POST)
    public ExecuteResult<Map<String,Object>> submitGoodToCart(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception{
        ExecuteResult result  = new ExecuteResult();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId",user.getUserId());






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
     * 收藏月嫂
     * #{maternityId},
     * #{uuid}
     */
    @RequestMapping(value = "submitCollection", method = RequestMethod.POST)
    public ExecuteResult<Map<String,Object>> submitCollection(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception{
        ExecuteResult result  = new ExecuteResult();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId",user.getUserId());
                int flag = collectionService.saveCollection(param);
                if (flag == 1) {
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
     * 取消收藏商品
     * #{maternityId},
     * #{uuid},
     */
    @RequestMapping(value = "cancelCollection", method = RequestMethod.POST)
    public ExecuteResult<Map<String,Object>> cancelCollection(@RequestBody Map<String,Object> param,HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId",user.getUserId());
                int flag = collectionService.cancelCollection(param);
                if (flag == 1) {
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
     * 提交商品评论
     * #{level}, 	  评价星际1-5
     * #{content},    评论内容
     * #{annoymous},  是否匿名  1是,0否'
     * #{uuid},       用户编号
     * #{goodId}      商品编号
	 * #{img}		  评论图片,多个以,分割'
	 * #{type}        商品类型 1百分比 2百分比 3线下服务商品
	 * #{orderId}	  订单id
	 * #{money}		 当前商品实付金额',
     */
    @RequestMapping(value = "submitComment", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult submitComment(@RequestBody Map<String,Object> param, HttpServletRequest request)throws Exception {
        ExecuteResult result  = new ExecuteResult();
        try {
            HttpSession session = request.getSession();
            String uuid=param.get("uuid").toString();
            User user=(User)session.getAttribute(uuid);
            if(user==null){
                result.setResultCode(ResultCode.TIME_OUT);
                result.setMessage("超时请重新登录");
            }else {
                param.put("userId",user.getUserId());
                List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
                commentService.saveComment(param);
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


}
