package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.dao.GestationDao;
import com.itee.bingsheng.entity.Gestation;
import com.itee.bingsheng.mybatis.dao.GestationMapper;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IGestationService;
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
 *@description:周期controller
 */
@Controller
@RequestMapping(value = "/gestation")
public class GestationController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(GestationController.class.getName());
	@Resource
	private GestationMapper gestationMapper;
	@Resource
	private GestationDao gestationDao;
	@Resource
	private IGestationService gestationService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String list() {
		return "gestation/list";
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
			pages.setContent(gestationMapper.pageList(pageSpecification));
			pages.setTotalElements(gestationMapper.pageCount(pageSpecification));
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
	public String toEdit(ModelMap modelMap, @ModelAttribute("gestation")Gestation gestation, @RequestParam(value = "id",required = false) Integer id){
		try {
			if (id!=null) {
				gestation =gestationDao.findById(id);
				modelMap.addAttribute("gestation", gestation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "gestation/edit";
		}
	}

	/**
	 * 添加或编辑
	 * @return
	 */
	@RequestMapping(value = "edit" ,method = RequestMethod.POST)
	public String edit(@ModelAttribute("gestation")Gestation gestation, ModelMap modelMap,@RequestParam("imgFile") MultipartFile imgFile){
		try {
			Gestation g=null;
			if(gestation.getId()!=null){
				g=gestationDao.findById(gestation.getId());
			}
			ExecuteResult<Map<String,Object>> result=null;
			if (imgFile!=null&&imgFile.getSize()>0) {
				result= QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{imgFile},g!=null? g.getImg():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							gestation.setImg(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,null,gestation.getId());
					}
				}
			}
			gestationService.edit(gestation);
		} catch (Exception e) {
			e.printStackTrace();
			return toEdit(modelMap,null,gestation.getId());
		}
		return "redirect:../common/success.do?reurl=gestation/page.do?explain=gestationdo";
	}
}
