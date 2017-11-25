package com.itee.bingsheng.web.action.base;


import com.itee.bingsheng.entity.Recommend;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IRecommendService;
import com.itee.bingsheng.utils.CreateUUIDUtil;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/recommend")
public class RecommendController extends BaseController {

    @Resource
    private IRecommendService service;


    @RequestMapping(value = "recommend", method = RequestMethod.GET)
    public String recommend() {
        return "recommend/recommendlist";
    }


    @RequestMapping(value = "recommendList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> recommendList(@RequestBody PageSpecification<Map<String, Object>> pageRequest, HttpServletRequest request)throws Exception {
        MybatisPage<Map<String, Object>> pages = new MybatisPage<Map<String, Object>>();
        Map<String, Object> param = new HashMap<>();
        param.put("phone", pageRequest.getData().get("phone"));
        param.put("name",pageRequest.getData().get("name"));
        param.put("pageSize", pageRequest.getPageSize());
        param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
        pages.setTotalElements(service.getAllDataCount(param));
        pages.setContent(service.queryAllData(param));
        return pages;
    }




    @RequestMapping(value = "toAddRecommend", method = RequestMethod.GET)
    public String toAddRecommend(@ModelAttribute("recommend") Recommend recommend, ModelMap modelMap, HttpServletRequest request) {
        return "recommend/addrecommend";
    }





    @RequestMapping(value = "enabledState")
    @ResponseBody
    public int enabledState(@RequestParam("id") int id, @RequestParam("state") boolean state)throws Exception {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("state",state);
        return service.updateData(map);
    }



    @RequestMapping(value = "addRecommend", method = RequestMethod.POST)
    public String addRecommend(@ModelAttribute("recommend") Recommend recommend,HttpServletRequest request)throws Exception {
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");// 获取上传文件名;

            if (file!=null && file.getSize()>0) {
                String fileKey = CreateUUIDUtil.createUUID();
                File uploadfile = new File(file.getOriginalFilename());
                FileOutputStream outputStream = new FileOutputStream(uploadfile);
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                boolean flag= QiNiuXiuXiuUtil.upload(fileKey,uploadfile);
                params.put("head",fileKey);
            }
            BL3Utils.mapRemoveNull(params);
            params.put("createor",SysUser.getId());
            service.saveData(params);
        }catch (Exception e) {
            e.printStackTrace();
            return "recommend/addrecommend";
        }
        return "recommend/recommendlist";
    }




    @RequestMapping(value = "toEditRecommend", method = RequestMethod.GET)
    private String toEditRecommend(@RequestParam("id") int id,ModelMap modelMap)throws Exception {
        Recommend recommend=service.getDateById(id);
        modelMap.addAttribute("recommend",recommend);
        return "recommend/editrecommend";
    }



    @RequestMapping(value = "updateRecommend", method = RequestMethod.POST)
    public String updateRecommend(@ModelAttribute("recommend") Recommend recommend,HttpServletRequest request)throws Exception {
        recommend=service.getDateById(recommend.getId());
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");// 获取上传文件名;
        try {
            boolean flag=true;
            if (file != null && file.getSize() > 0) {
                String fileKey = CreateUUIDUtil.createUUID();
                File uploadfile = new File(file.getOriginalFilename());
                FileOutputStream outputStream = new FileOutputStream(uploadfile);
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                if (StringUtils.isNotEmpty(recommend.getHead())) {
                    flag = QiNiuXiuXiuUtil.updataFile(recommend.getHead(), fileKey, uploadfile);
                } else {
                    flag = QiNiuXiuXiuUtil.upload(fileKey, uploadfile);
                }
                params.put("head", fileKey);
            }
            BL3Utils.mapRemoveNull(params);
            service.saveData(params);
            service.updateData(params);
        }catch (Exception e) {
            e.printStackTrace();
            return "recommend/editrecommend";
        }
        return "recommend/recommendlist";
    }


}
