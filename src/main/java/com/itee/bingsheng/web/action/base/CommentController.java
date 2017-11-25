package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ICommentService;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 月嫂评论管理
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController extends BaseController{

	protected final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());

	@Resource
	private ICommentService service;


	/**
	 * 进入评论列表页面
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "comment", method = RequestMethod.GET)
    public String comment() throws Exception{
        return "comment/commentlist";
    }


	/**
	 * 加载数据
	 * @param pageRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "commentlist", method = RequestMethod.POST)
	@ResponseBody
	public MybatisPage<Map<String,Object>> commentlist(@RequestBody PageSpecification pageRequest)throws Exception {
		MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
		try{
			Map<String, Object> param = new HashMap<>();
			param.put("phone", pageRequest.getData().get("phone"));
			param.put("name", pageRequest.getData().get("name"));
			String level=pageRequest.getData().get("level").toString();
			if(StringUtils.isNotEmpty(level)){
				param.put("level",level);
			}
			pages.setTotalElements(service.getAllCount(param));
			param.put("pageSize", pageRequest.getPageSize());
			param.put("page", (pageRequest.getPage() - 1) * pageRequest.getPageSize());
			List<Map<String,Object>> list = service.queryAllComment(param);
			pages.setContent(list);
		}catch (Exception e){
			e.printStackTrace();
		}
		return pages;
	}



	/**
	 * 更新评论状态
	 * @param id
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "enabledState")
	@ResponseBody
	public int enabledState(@RequestParam("id") int id, @RequestParam("state") boolean state,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
		Map<String,Object> map=new HashMap<>();
		map.put("id",id);
		map.put("state",state);
		map.put("operator",SysUser.getId());
		return service.updateComment(map);
	}


	/**
	 * 批量审核评论是否展示
	 * @param ids
	 * @param state
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "extensionApply",method = RequestMethod.POST)
	@ResponseBody
	public int extensionApply(@RequestParam(value="ids[]") String [] ids, @RequestParam("state") int state, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
			Map<String,Object> map=new HashMap<>();
			map.put("ids", Arrays.asList(ids));
			map.put("state",state);
			map.put("operator",SysUser.getId());
			return  service.bathUpdateComment(map);
		} catch (Exception e) {
			e.printStackTrace();
			return  0;
		}finally {
			return 1;
		}
	}
}
