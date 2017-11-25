package com.itee.bingsheng.web.action.common;


import com.itee.bingsheng.mybatis.dao.CommonMapper;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping(value = "/common")
public class CommonController {
    @Resource
    private CommonMapper commonMapper;

    @RequestMapping(value = "success", method = RequestMethod.GET)
    public String success() {
        return "common/success";
    }

    @RequestMapping(value = "failure", method = RequestMethod.GET)
    public String simplefailure() {
        return "common/failure";
    }

    @RequestMapping(value = "about")
    public String about() {
        return "common/about";
    }

    @RequestMapping(value = "help")
    public String help() {
        return "common/help";
    }

    @RequestMapping(value = "denial")
    public String denial() {
        return "common/denial";
    }


    @RequestMapping(value = "error_500", method = RequestMethod.GET)
    public String error_500() {
        return "common/error_500";
    }

    @RequestMapping(value = "error_404", method = RequestMethod.GET)
    public String error_404() {
        return "common/error_404";
    }

    @RequestMapping(value = "error_403", method = RequestMethod.GET)
    public String error_503() {
        return "common/error_403";
    }
    /**
     * 软删除公共函数
     * @return
     */
    @RequestMapping(value = "softDelete" ,method = RequestMethod.GET)
    @ResponseBody
    public int softDelete(@RequestParam("tn") String tn,@RequestParam("state") Integer state,@RequestParam("id") Integer id){
        int flag=1;
        try {
            commonMapper.softDelete(tn,state,id);
        } catch (Exception e) {
            flag=0;
            e.printStackTrace();
        } finally {
            return flag;
        }
    }
    /***批量上下架
     * @param tn:表名
     * @param ids：id数组
     * @param shelf:1-上架；0-下架
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "batchShelf", method = RequestMethod.POST)
    public ResponseEntity<ExecuteResult> batchShelf(@RequestParam("tn") String tn,@RequestParam("ids[]") Integer [] ids, @RequestParam("shelf") Integer shelf)throws Exception {
        ExecuteResult result  = new ExecuteResult<>();
        try {
            if (ids!= null&&ids.length>0) {
                commonMapper.batchShelf(tn,ids,shelf);
                result.setResultCode(ResultCode.SUCCESS);
            }else{
                result.setResultCode(ResultCode.FAIL);
                result.setMessage("请选择操作项！");
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("系统出现异常请稍后再试！");
            e.printStackTrace();
        }finally {
            return new ResponseEntity<ExecuteResult>(result, HttpStatus.OK);
        }
    }

}
