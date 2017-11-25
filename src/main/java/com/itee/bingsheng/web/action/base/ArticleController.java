package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.Article;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IArticleService;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController extends BaseController{

    @Resource
    private IArticleService service;


    @RequestMapping(value = "article", method = RequestMethod.GET)
    public String article() {
        return "article/articlelist";
    }


    @RequestMapping(value = "articlelist", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> articlelist(@RequestBody PageSpecification<Article> pageRequest, ModelMap modelMap)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("pageSize", pageRequest.getPageSize());
            param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
            String state=pageRequest.getData().get("state").toString();

            if(org.apache.commons.lang3.StringUtils.isNotEmpty(state)){
                param.put("state",Integer.parseInt(state));
            }
            List<Map<String,Object>> list = service.queryAllArticle(param);
            pages.setContent(list);
            pages.setTotalElements(service.getAllCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }


    @RequestMapping(value = "toAddArticle", method = RequestMethod.GET)
    public String toAddArticle(@ModelAttribute("article") Article article, ModelMap modelMap, HttpServletRequest request) {
        return "article/addArticle";
    }





    @RequestMapping(value = "enabledState")
    @ResponseBody
    public int enabledState(@RequestParam("id") int id, @RequestParam("state") boolean state)throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("state",state);
        return service.updateArticle(map);
    }



    @RequestMapping(value = "addArticle", method = RequestMethod.POST)
    public String addArticle(@ModelAttribute("article") Article article,HttpServletRequest request)throws Exception {
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);

        Map<String ,Object>map=new HashMap<String, Object>();
        map.put("remark",article.getRemark());
        map.put("link",article.getLink());
        map.put("type",article.getType());
        map.put("title",article.getTitle());
        map.put("createTime",new Date());
        map.put("creator",SysUser.getId());
        map.put("author",article.getAuthor());
        map.put("authorEmail",article.getAuthorEmail());
        map.put("publishTime",article.getPublishTime());
        map.put("keywords",article.getKeywords());
        service.saveArticle(map);
        return "article/articlelist";   //保存成功
    }




    @RequestMapping(value = "toEditArticle", method = RequestMethod.GET)
    private String toEditArticle(@RequestParam("id") int id,ModelMap modelMap)throws Exception {
        Article article=service.getArticle(id);
        modelMap.addAttribute("article",article);
        return "article/editArticle";
    }



    @RequestMapping(value = "updateArticle", method = RequestMethod.POST)
    public String updateArticle(@ModelAttribute("article") Article article,HttpServletRequest request)throws Exception {
        Map<String ,Object>map=new HashMap<String, Object>();
        map.put("remark",article.getRemark());
        map.put("title",article.getTitle());
        map.put("link",article.getLink());
        map.put("createTime",new Date());
        map.put("author",article.getAuthor());
        map.put("authorEmail",article.getAuthorEmail());
        map.put("publishTime",article.getPublishTime());
        map.put("keywords",article.getKeywords());
        map.put("id",article.getId());
        service.updateArticle(map);
        return "article/articlelist";   //保存成功
    }

    @RequestMapping(value = "deleteArtic")
    @ResponseBody
    public int deleteArtic(@RequestParam("id") int id)throws Exception {
        return service.deleteArtic(id);
    }

}
