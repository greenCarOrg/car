package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.Manufactor;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IManufactorService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/manufactor")
public class ManufactorController extends BaseController{

    @Resource
    private IManufactorService service;


    @RequestMapping(value = "manufactor", method = RequestMethod.GET)
    public String manufactor() {
        return "manufactor/manufactorList";
    }


    @RequestMapping(value = "manufactorList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> manufactorList(@RequestBody PageSpecification<Map<String,Object>> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            param.put("name",pageRequest.getData().get("name"));
            param.put("phone",pageRequest.getData().get("phone"));
            param.put("contacts",pageRequest.getData().get("contacts"));
            param.put("type",pageRequest.getData().get("type"));
            List<Map<String,Object>> list = service.queryAllManufactor(param);
            pages.setContent(list);
            pages.setTotalElements(service.getAllCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }



    @RequestMapping(value = "toAddManufactor", method = RequestMethod.GET)
    public String toAddAdvertisement(@ModelAttribute("manufactor") Manufactor manufactor, ModelMap modelMap, HttpServletRequest request) {
        return "manufactor/addManufactor";
    }





    @RequestMapping(value = "enabledState")
    @ResponseBody
    public int enabledState(@RequestParam("id") int id, @RequestParam("state") boolean state)throws Exception {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("state",state);
        return service.updateManufactor(map);
    }



    @RequestMapping(value = "addManufactor", method = RequestMethod.POST)
    public String addManufactor(@ModelAttribute("manufactor") Manufactor manufactor,HttpServletRequest request)throws Exception {
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        try {
            Map<String ,Object>map=new HashMap<>();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");// 获取上传文件名;
            MultipartFile licensefile = multipartRequest.getFile("licensefile");// 获取上传文件名;
            String  url=null,fileKey=null,license=null;
            if (file!=null && file.getSize()>0 && licensefile!=null &&licensefile.getSize()>0) {
                fileKey = CreateUUIDUtil.createUUID();
                File uploadfile = new File(file.getOriginalFilename());
                FileOutputStream outputStream = new FileOutputStream(uploadfile);
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();

                File uploadlicensefile = new File(licensefile.getOriginalFilename());
                FileOutputStream outputStream1 = new FileOutputStream(uploadlicensefile);
                outputStream1.write(licensefile.getBytes());
                outputStream1.flush();
                outputStream1.close();

                boolean flag=QiNiuXiuXiuUtil.upload(fileKey,uploadfile);
                //返回生产执照不可修改
                license=CreateUUIDUtil.createUUID();
                boolean lc=QiNiuXiuXiuUtil.upload(license,uploadlicensefile);
                map.put("img",fileKey);
                map.put("license",license);
            }else{
                return "manufactor/addManufactor";
            }
            map.put("name",manufactor.getName());
			map.put("bond",manufactor.getBond());
            map.put("phone",manufactor.getPhone());
            map.put("contacts",manufactor.getContacts());
            map.put("state",manufactor.getState());
            map.put("address",manufactor.getAddress());

            map.put("createTime",new Date());
			map.put("idCard",manufactor.getIdCard());
            map.put("operator",SysUser.getId());
			map.put("remark",manufactor.getRemark());
            map.put("customerPhone",manufactor.getCustomerPhone());

            service.saveManufactor(map);
        }catch (Exception e) {
            e.printStackTrace();
            return "manufactor/addManufactor";
        }
        return "manufactor/manufactorList";
    }




    @RequestMapping(value = "toEditManufactor", method = RequestMethod.GET)
    private String toEditManufactor(@RequestParam("id") int id,ModelMap modelMap)throws Exception {
        Manufactor manufactor=service.getManfactorById(id);
        modelMap.addAttribute("manufactor",manufactor);
        return "manufactor/editManufactor";
    }



    @RequestMapping(value = "updateManufactor", method = RequestMethod.POST)
    public String updateManufactor(@ModelAttribute("manufactor") Manufactor manufactor,HttpServletRequest request)throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");// 获取上传文件名;
        try {
            Map<String, Object> map = new HashMap<>();
             String videourl=null,videoId=null;
            boolean flag=true;
            if (file != null && file.getSize() > 0) {
                String fileKey = CreateUUIDUtil.createUUID();
                File uploadfile = new File(file.getOriginalFilename());
                FileOutputStream outputStream = new FileOutputStream(uploadfile);
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                if (StringUtils.isNotEmpty(manufactor.getImg())) {
                    flag = QiNiuXiuXiuUtil.updataFile(manufactor.getImg(), fileKey, uploadfile);
                } else {
                    flag = QiNiuXiuXiuUtil.upload(fileKey, uploadfile);
                }
                map.put("img", fileKey);
            }
            map.put("name",manufactor.getName());
            map.put("phone",manufactor.getPhone());
            map.put("contacts",manufactor.getContacts());
            map.put("state",manufactor.getState());
            map.put("address",manufactor.getAddress());
			map.put("bond",manufactor.getBond());
			map.put("remark",manufactor.getRemark());
            map.put("id",manufactor.getId());
            map.put("customerPhone",manufactor.getCustomerPhone());
            service.updateManufactor(map);
        }catch (Exception e) {
            e.printStackTrace();
            return "manufactor/editManufactor";
        }
        return "manufactor/manufactorList";
    }


}
