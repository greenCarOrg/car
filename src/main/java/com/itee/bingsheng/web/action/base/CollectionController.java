package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ICollectionService;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 收藏商品
 */
@Controller
@RequestMapping(value = "/collection")
public class CollectionController extends BaseController{

	protected final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());

	@Resource
	private ICollectionService service;

    @RequestMapping(value = "collection", method = RequestMethod.GET)
    public String collection() throws Exception{
        return "info/collectionlist";
    }


	@RequestMapping(value = "collectionList", method = RequestMethod.POST)
	@ResponseBody
	public MybatisPage<Map<String,Object>> goodsCollectionList(@RequestBody PageSpecification pageRequest)throws Exception {
		MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
		try{
			Map<String, Object> param = new HashMap<>();
			param.put("pageSize", pageRequest.getPageSize());
			param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
			param.put("name", pageRequest.getData().get("name"));
			List<Map<String,Object>> list = service.queryAllCollection(param);
			pages.setContent(list);
			pages.setTotalElements(service.getAllCount(param));
		}catch (Exception e){
			e.printStackTrace();
		}
		return pages;
	}






}
