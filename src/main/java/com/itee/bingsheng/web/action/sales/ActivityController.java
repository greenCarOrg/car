package com.itee.bingsheng.web.action.sales;

import com.itee.bingsheng.mybatis.dao.ActivityMDao;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.utils.core.web.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *@description:活动controller
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(ActivityController.class.getName());
	@Resource
	private ActivityMDao activityMDao;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "activity", method = RequestMethod.GET)
	public String suppliers() {
		return "sales/activity";
	}

	/**
	 * 列表
	 * @param pageSpecification
	 * @return
	 */
	@RequestMapping(value = "page" ,method = RequestMethod.POST)
	@ResponseBody
	public MybatisPage page(@RequestBody PageSpecification<Map<String,Object>> pageSpecification){
		MybatisPage<Map<String, Object>> pages = new MybatisPage<Map<String, Object>>();
		try {
			pageSpecification.setPage((pageSpecification.getPage() - 1) * pageSpecification.getPageSize());
			pages.setContent(activityMDao.pageList(pageSpecification));
			pages.setTotalElements(activityMDao.pageCount(pageSpecification));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return pages;
		}
	}

	/**
	 * 跳转编辑页面
	 * @return
	 */
	@RequestMapping(value = "toEdit" ,method = RequestMethod.GET)
	public String toEdit(ModelMap modelMap,HttpServletRequest request){
		try {
			params = WebUtils.getParamAsDto(request);
			BL3Utils.mapRemoveNull(this.params);
			if (params!= null&&params.size()>0) {
				List<Map<String,Object>> entityList = activityMDao.findOne(params);
				if (entityList!= null&&entityList.size()>0) {
					modelMap.put("entity",entityList.get(0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "sales/editActivity";
		}
	}

	/**
	 * 添加或编辑
	 * @return
	 */
	@RequestMapping(value = "edit" ,method = RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request){
		params = WebUtils.getParamAsDto(request);
		BL3Utils.mapRemoveNull(this.params);
		code="0";
		try {
			//校验活动名称是否重复
			if(params.get("act_id") == null){
				activityMDao.add(params);
			}else{
				activityMDao.update(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return getResult().toString();
		}
	}

	/**
	 * 删除活动
	 * @return
	 */
	@RequestMapping(value = "delete" ,method = RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam("id") int id){
		try {
			activityMDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return 1;
		}
	}
}
