package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.dao.ServiceDao;
import com.itee.bingsheng.entity.TService;
import com.itee.bingsheng.mybatis.dao.ServiceMapper;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IServiceService;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

/**
 *@description:月嫂controller
 */
@Controller
@RequestMapping(value = "/service")
public class ServiceController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(ServiceController.class.getName());
	@Resource
	private ServiceMapper serviceMapper;
	@Resource
	private ServiceDao serviceDao;
	@Resource
	private IServiceService serviceService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String list() {
		return "service/list";
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
			pages.setContent(serviceMapper.pageList(pageSpecification));
			pages.setTotalElements(serviceMapper.pageCount(pageSpecification));
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
	public String toEdit(ModelMap modelMap,@ModelAttribute("tService")TService tService,@RequestParam(value = "id",required = false) Integer id){
		try {
			if (id!=null) {
				tService =serviceDao.findById(id);
				modelMap.addAttribute("tService", tService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "service/edit";
		}
	}

	/**
	 * 添加或编辑
	 * @return
	 */
	@RequestMapping(value = "edit" ,method = RequestMethod.POST)
	public String edit(@ModelAttribute("tService")TService tService, ModelMap modelMap,@RequestParam("img") MultipartFile img){
		try {
			TService s=null;
			if(tService.getId()!=null){
				s=serviceDao.findById(tService.getId());
			}
			ExecuteResult<Map<String,Object>> result=null;
			if (img!=null&&img.getSize()>0) {
				result= QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{img},s!=null?s.getImgKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							tService.setImgKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,null,tService.getId());
					}
				}
			}
			serviceService.edit(tService);
		} catch (Exception e) {
			e.printStackTrace();
			return toEdit(modelMap,null,tService.getId());
		}
		return "redirect:../common/success.do?reurl=service/page.do?explain=pagedo";
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "delete" ,method = RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam("id") int id){
		int flag=1;
		try {
			serviceService.updateState(0,id);
		} catch (Exception e) {
			flag=0;
			e.printStackTrace();
		} finally {
			return flag;
		}
	}
}
