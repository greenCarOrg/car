package com.itee.bingsheng.web.action.driver;

import com.itee.bingsheng.dao.SsDao;
import com.itee.bingsheng.entity.SpecialistService;
import com.itee.bingsheng.mybatis.dao.SsMapper;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.persistence.Sessions;
import com.itee.bingsheng.service.SsService;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.logging.Logger;

/**
 *@description:专家服务controller
 */
@Controller
@RequestMapping(value = "/ss")
public class SsController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(SsController.class.getName());
	@Resource
	private SsMapper ssMapper;
	@Resource
	private SsDao ssDao;
	@Resource
	private SsService ssService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String maternity() {
		return "ss/list";
	}
	/**
	 * 列表
	 * @param pageSpecification
	 * @return
	 */
	@RequestMapping(value = "list" ,method = RequestMethod.POST)
	@ResponseBody
	public MybatisPage page(@RequestBody PageSpecification<Map<String,Object>> pageSpecification){
		MybatisPage<Map<String, Object>> pages = new MybatisPage<Map<String, Object>>();
		try {
			pageSpecification.setPage((pageSpecification.getPage() - 1) * pageSpecification.getPageSize());
			pages.setContent(ssMapper.pageList(pageSpecification));
			pages.setTotalElements(ssMapper.pageCount(pageSpecification));
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
	public String toEdit(ModelMap modelMap,@ModelAttribute("specialistService") SpecialistService specialistService, @RequestParam(value = "id",required = false) Integer id){
		try {
			if (id!=null) {
				specialistService=ssDao.findById(id);
				modelMap.addAttribute("entity",specialistService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "ss/edit";
		}
	}

	/**
	 * 添加或编辑
	 * @return
	 */
	@RequestMapping(value = "edit" ,method = RequestMethod.POST)
	public String edit(@ModelAttribute("specialistService")SpecialistService specialistService,ModelMap modelMap,HttpServletRequest request,
					   @RequestParam("imgFile") MultipartFile imgFile){
		try {
			SpecialistService ss=null;
			if(specialistService.getId()!=null){
				ss=ssDao.findById(specialistService.getId());
			}
			ExecuteResult<Map<String,Object>> result=null;
			if (imgFile!=null&&imgFile.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{imgFile},ss!=null?ss.getImg():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialistService.setImg(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,null,specialistService.getId());
					}
				}
			}
			specialistService.setSysuserId(Sessions.getSysUser(request).getId());
			ssService.edit(specialistService);
		} catch (Exception e) {
			e.printStackTrace();
			return toEdit(modelMap,null,specialistService.getId());
		}
		return "redirect:../common/success.do?reurl=ss/page.do?explain=ssdo";
	}
}
