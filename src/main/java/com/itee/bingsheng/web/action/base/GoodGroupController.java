package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.GoodGroup;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IGoodGroupService;
import com.itee.bingsheng.utils.CreateUUIDUtil;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import com.itee.bingsheng.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品分组
 */
@Controller
@RequestMapping(value = "/goodGroup")
public class GoodGroupController {

    @Resource
    private IGoodGroupService service;

    @RequestMapping(value = "goodGroup", method = RequestMethod.GET)
    public String goodGroup() {
        return "goodgroup/goodgrouplist";
    }

    @RequestMapping(value = "goodGroupList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> goodGroupList(@RequestBody PageSpecification<GoodGroup> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            String isparent=pageRequest.getData().get("isparent").toString();

            if(org.apache.commons.lang3.StringUtils.isNotEmpty(isparent)){
                param.put("isparent",Integer.parseInt(isparent));
            }
            String state=pageRequest.getData().get("state").toString();

            if(org.apache.commons.lang3.StringUtils.isNotEmpty(state)){
                param.put("state",Integer.parseInt(state));
            }

            List<Map<String,Object>> list  = service.queryAllGroup(param);
            pages.setContent(list);
            pages.setTotalElements(service.queryAllGroupCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }



    @RequestMapping(value = "toAddGoodGroup", method = RequestMethod.GET)
    public String toAddGoodGroup(@ModelAttribute("goodGroup") GoodGroup goodGroup,HttpServletRequest request)throws Exception {
        List<Map<String,Object>> list = service.queryFirstGroupList();
        request.setAttribute("list",list);
        return "goodgroup/addgoodgroup";
    }





    @RequestMapping(value = "addGoodGroup", method = RequestMethod.POST)
    public String addGoodGroup(@ModelAttribute("goodGroup") GoodGroup goodGroup,HttpServletRequest request)throws Exception {
        try {
            String isparent = request.getParameter("isparent");
            if (isparent != null && Integer.valueOf(isparent).intValue() == 0) {
                String parentid = request.getParameter("parentid");
                goodGroup.setParentId(Integer.valueOf(parentid));
            } else {
                goodGroup.setParentId(0);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("name", goodGroup.getName());
            map.put("orderNum", goodGroup.getOrderNum());
            SysUser sysUser = (SysUser) request.getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);
            map.put("creator", sysUser.getId());
            map.put("parentId", goodGroup.getParentId());
            map.put("remark", goodGroup.getRemark());
            map.put("hot", goodGroup.getHot());

            map.put("createTime", new Date());
            int id = service.saveGoodGroup(map);
            if (id == 0) {
                request.setAttribute("type", goodGroup.getType());
                return "goodgroup/addgoodgroup";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "goodgroup/goodgrouplist";
    }



    @RequestMapping(value = "toEditGoodGroup", method = RequestMethod.GET)
    private String toEditGoodGroup(@RequestParam("id") int id,ModelMap modelMap)throws Exception {
        GoodGroup goodGroup=service.getGoodGroupById(id);
        modelMap.addAttribute("goodGroup",goodGroup);
        List<Map<String,Object>> list = service.queryFirstGroupList();
        modelMap.addAttribute("list",list);
        return "goodgroup/editgoodgroup";
    }

    /**
     * 启用分组
     * @param id
     * @param state
     * @param request
     * @return
     */
    @RequestMapping(value = "enabledState")
    @ResponseBody
    public int enabledState(@RequestParam("id") int id, @RequestParam("state") boolean state,HttpServletRequest request)throws Exception {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("state",state);
        service.updateGoodGroup(map);
        return id;
    }


    @RequestMapping(value = "updateGoodGroup", method = RequestMethod.POST)
    public String updateGoodGroup(@ModelAttribute("goodGroup") GoodGroup goodGroup,HttpServletRequest request)throws Exception {
        try{
            Map<String,Object> map=new HashMap<>();
            String isparent = request.getParameter("isparent");
            if(isparent != null && Integer.valueOf(isparent).intValue() == 0){
                String parentid = request.getParameter("parentid");
                map.put("parentId",parentid);
            }else{
                map.put("parentId",0);
            }
            map.put("name",goodGroup.getName());
            map.put("orderNum",goodGroup.getOrderNum());
            map.put("remark",goodGroup.getRemark());
            map.put("hot",goodGroup.getHot());
            map.put("state",goodGroup.getState());
            map.put("id",goodGroup.getId());
            int id = service.updateGoodGroup(map);
            if(id ==0){
                return "goodgroup/editgoodgroup";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "goodgroup/goodgrouplist";
    }
}
