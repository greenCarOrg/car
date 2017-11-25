package com.itee.bingsheng.web.action.rest;

import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.service.IGoodGroupService;
import com.itee.bingsheng.service.IGoodService;
import com.itee.bingsheng.service.IManufactorService;

import com.itee.bingsheng.web.action.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController("groupController")
@RequestMapping(value = "/rest")
public class GroupController extends BaseController {

    @Resource
    IGoodGroupService service;
    @Resource
    IManufactorService manufactorService;
    @Resource
    IGoodService goodService;

    /**
     * 获取分组信息
     * @param type 1百分百  2百分比   3线下服务商
     * @return
     */
    @RequestMapping(value = "getGroup", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getGroupInfo(@RequestParam("type") int type) throws Exception{
        ExecuteResult<Map<String,Object>> result  = new ExecuteResult<>();
        try {
            Map<String,Object> param=new HashMap();
            param.put("groupId",0);
            param.put("type",type);
            List<Map<String ,Object>> data=service.queryAllGroup(param);
            param.clear();
            param.put("data",data);
            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(param);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 获取子分组信息
     * @param groupId 1百分百  2百分比   3线下服务商
     * @return
     */
    @RequestMapping(value = "getSubGroup", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getSubGroup(@RequestParam("groupId") int groupId ) throws Exception{
        ExecuteResult<Map<String,Object>> result  = new ExecuteResult<>();
        try {
           Map<String ,Object> param=new HashMap<>();
            param.put("groupId",groupId);
            param.put("data",service.queryAllGroup(param));
            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(param);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 获取品牌信息
     * @param type 1百分百 2百分比  3线下服务商
     *             品牌细信息才适用分页,点击更多下拉
     *             page 当前页数
     *             pageSize 当前每页显示的数量
     * @return
     */
    @RequestMapping(value = "getBrand", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getBrand(@RequestParam("type") int type,@RequestParam("page") int page,@RequestParam("pageSize") int pageSize) throws Exception{
        ExecuteResult<Map<String,Object>> result  = new ExecuteResult<>();
        try {
            Map<String,Object> param=new HashMap<>();
            param.put("type",type);
            param.put("page",page);
            param.put("pageSize",pageSize);
            List<Map<String ,Object>> data=new ArrayList<>();
            int total=0;

            data = manufactorService.queryAllManufactor(param);
            total = manufactorService.getAllCount(param);

            param.put("data",data);
            param.put("totalElement",total);
            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(param);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 点击相应品牌进入相应的品牌页面 品牌详情
     * type 1百分百 2百分比  3线下服务
     * 百分百和百分比是厂家,线下服务是服务商编号
     */
    @RequestMapping(value = "getBrandById", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getBrandById(@RequestParam("brandId") int brandId,@RequestParam("type") int type,HttpServletRequest request)throws Exception{
        ExecuteResult result  = new ExecuteResult<>();
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("id",brandId);
            map.put("data",manufactorService.getManfactorById(brandId));

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
     * 点击相应品牌进入相应的品牌页面  品牌商品列表
     * type 1百分百 2百分比  3线下服务
     * 百分百和百分比是厂家,线下服务是服务商编号
     */
    @RequestMapping(value = "getGoodsByBrandId", method = RequestMethod.GET)
    public ExecuteResult<Map<String,Object>> getGoodsByBrandId(@RequestParam("brandId") int brandId,@RequestParam("type") int type,HttpServletRequest request)throws Exception{
        ExecuteResult result  = new ExecuteResult<>();
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("suppliers_id",brandId);
            map.put("type",type);
            int totalElement=goodService.queryGoodListCount(map);
            List<Map<String,Object>>data= goodService.queryGoodList(map);
            map.put("data",data);
            map.put("totalElement",totalElement);
            result.setResultCode(ResultCode.SUCCESS);
            result.setResult(map);
        } catch (Exception e) {
            result.setResultCode(ResultCode.FAIL);
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
