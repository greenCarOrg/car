package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.Cycle;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ICycleService;
import com.itee.bingsheng.utils.CreateUUIDUtil;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cycle")
public class CycleController extends BaseController{

    @Resource
    private ICycleService service;


    @RequestMapping(value = "cycle", method = RequestMethod.GET)
    public String cycle() {
        return "cycle/cyclelist";
    }


    @RequestMapping(value = "cycleList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> cycleList(@RequestBody PageSpecification<Map<String,Object>> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            param.put("state",pageRequest.getData().get("state"));
            List<Map<String,Object>> list = service.queryAllCycle(param);
            pages.setContent(list);
            pages.setTotalElements(service.getAllCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }



    @RequestMapping(value = "toAddCycle", method = RequestMethod.GET)
    public String toAddCycle(@ModelAttribute("cycle") Cycle cycle,HttpServletRequest request)throws Exception {
        return "cycle/addcycle";
    }



    @RequestMapping(value = "enabledState")
    @ResponseBody
    public int enabledState(@RequestParam("id") int id, @RequestParam("state") boolean state)throws Exception {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("state",state);
        return service.updateCycle(map);
    }



    @RequestMapping(value = "deleteCycle")
    @ResponseBody
    public int deleteCycle(@RequestParam("id") int id)throws Exception {
        return service.deleteCycleById(id);
    }

    @RequestMapping(value = "addCycle", method = RequestMethod.POST)
    public String addCycle(@ModelAttribute("cycle") Cycle cycle,HttpServletRequest request)throws Exception {
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        try {
            Map<String ,Object>map=new HashMap<>();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");// 获取上传文件名;
            if (file!=null && file.getSize()>0) {
                String fileKey = CreateUUIDUtil.createUUID();
                File uploadfile = new File(file.getOriginalFilename());
                FileOutputStream outputStream = new FileOutputStream(uploadfile);
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                boolean flag=QiNiuXiuXiuUtil.upload(fileKey,uploadfile);
                map.put("img",fileKey);
            }
            map.put("level",cycle.getLevel());
			map.put("state",cycle.getState());
            map.put("creator",SysUser.getId());
            map.put("remark",cycle.getRemark());
            service.saveCycle(map);
        }catch (Exception e) {
            e.printStackTrace();
            return "cycle/addcycle";
        }
        return "cycle/cyclelist";
    }




    @RequestMapping(value = "toEditCycle", method = RequestMethod.GET)
    private String toEditCycle(@RequestParam("id") int id,ModelMap modelMap)throws Exception {
        Cycle cycle=service.getCycleById(id);
        modelMap.addAttribute("cycle",cycle);
        return "cycle/editcycle";
    }



    @RequestMapping(value = "updateManufactor", method = RequestMethod.POST)
    public String updateManufactor(@ModelAttribute("cycle") Cycle cycle,HttpServletRequest request)throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");// 获取上传文件名;
        try {
            Map<String, Object> map = new HashMap<>();
            if (file != null && file.getSize() > 0) {
                Boolean flag=true;
                String fileKey = CreateUUIDUtil.createUUID();
                File uploadfile = new File(file.getOriginalFilename());
                FileOutputStream outputStream = new FileOutputStream(uploadfile);
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                if (StringUtils.isNotEmpty(cycle.getImg())) {
                    flag = QiNiuXiuXiuUtil.updataFile(cycle.getImg(), fileKey, uploadfile);
                } else {
                    flag = QiNiuXiuXiuUtil.upload(fileKey, uploadfile);
                }
                map.put("img", fileKey);
            }
            map.put("level",cycle.getLevel());
            map.put("state",cycle.getState());
            map.put("id",cycle.getId());
            map.put("remark",cycle.getRemark());
            service.updateCycle(map);
        }catch (Exception e) {
            e.printStackTrace();
            return "cycle/editcycle";
        }
        return "cycle/cyclelist";
    }


}
