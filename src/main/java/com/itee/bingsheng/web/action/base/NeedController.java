package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.dao.NeedDao;
import com.itee.bingsheng.mybatis.dao.NeedMapper;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.NeedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *@description:用户需求controller
 */
@Controller
@RequestMapping(value = "/need")
public class NeedController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(NeedController.class.getName());
	@Resource
	private NeedMapper needMapper;
	@Resource
	private NeedDao NeedDao;
	@Resource
	private NeedService needService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String list() {
		return "need/list";
	}
	/**
	 * 列表
	 * @param pageSpecification
	 * @return
	 */
	@RequestMapping(value = "list" ,method = RequestMethod.POST)
	@ResponseBody
	public MybatisPage list(@RequestBody PageSpecification<Map<String,Object>> pageSpecification){
		MybatisPage<Map<String, Object>> pages = new MybatisPage<Map<String, Object>>();
		try {
			pageSpecification.setPage((pageSpecification.getPage() - 1) * pageSpecification.getPageSize());
			List<Map<String,Object>> contentList = needMapper.pageList(pageSpecification);
			if(contentList!= null&&contentList.size()>0){
				String [] levels={};
				StringBuffer levelStr=null;
				for(Map<String,Object> content:contentList){
				    if (content!= null&&content.get("level")!=null) {
						levelStr=new StringBuffer("");
						levels=content.get("level").toString().split(",");
						for(String level:levels){
						    if ("1" .equals(level)) {
								levelStr.append("T3高级月嫂,");
						    }else if("2" .equals(level)){
								levelStr.append("T4骨干月嫂,");
						    }else if("3" .equals(level)){
								levelStr.append("3-T5专家月嫂,");
						    }else if("4" .equals(level)){
								levelStr.append("T6权威月嫂,");
						    }else{
								levelStr.append("T7首席月嫂,");
							}
						}
						content.put("level",levelStr.toString().substring(0,levelStr.length()-1));
				    }
				}
			}
			pages.setContent(contentList);
			pages.setTotalElements(needMapper.pageCount(pageSpecification));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return pages;
		}
	}
	/**
	 * 修改需求状态
	 * @return
	 */
	@RequestMapping(value = "updateState" ,method = RequestMethod.GET)
	@ResponseBody
	public int updateState(@RequestParam("id") int id,@RequestParam("state") int state){
		int flag=1;
		try {
			needService.updateState(state,id);
		} catch (Exception e) {
			flag=0;
			e.printStackTrace();
		} finally {
			return flag;
		}
	}
}
