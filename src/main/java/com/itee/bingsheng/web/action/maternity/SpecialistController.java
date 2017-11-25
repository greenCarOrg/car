package com.itee.bingsheng.web.action.maternity;

import com.itee.bingsheng.dao.SpecialistDao;
import com.itee.bingsheng.entity.Specialist;
import com.itee.bingsheng.mybatis.dao.SpecialistMapper;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ICommonService;
import com.itee.bingsheng.service.SpecialistService;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

/**
 *@description:专家controller
 */
@Controller
@RequestMapping(value = "/specialist")
public class SpecialistController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(SpecialistController.class.getName());
	@Resource
	private SpecialistMapper specialistMapper;
	@Resource
	private SpecialistDao specialistDao;
	@Resource
	private SpecialistService specialistService;
	@Resource
	private ICommonService commonService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String maternity() {
		return "specialist/list";
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
			pages.setContent(specialistMapper.pageList(pageSpecification));
			pages.setTotalElements(specialistMapper.pageCount(pageSpecification));
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
	public String toEdit(ModelMap modelMap,@RequestParam(value = "id",required = false) Integer id){
		try {
			if (id!=null) {
				Specialist specialist=specialistDao.findById(id);
				if(StringUtils.isNotEmpty(specialist.getImageKey())){
					String [] images=specialist.getImageKey().split(",");
					modelMap.addAttribute("images",images);
				}
				if(StringUtils.isNotEmpty(specialist.getIdCardKey())){
					String [] idCardImgs=specialist.getIdCardKey().split(",");
					modelMap.addAttribute("idCardImgs",idCardImgs);
				}
				modelMap.addAttribute("entity",specialist);
			}
			//获取省市区
			params.clear();
			params.put("level",1);
			modelMap.put("provinceList",commonService.getRegion(params));
			params.put("level",2);
			modelMap.put("cityList",commonService.getRegion(params));
			params.put("level",3);
			modelMap.put("districtList",commonService.getRegion(params));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "specialist/edit";
		}
	}

	/**
	 * 添加或编辑
	 * @return
	 */
	@RequestMapping(value = "edit" ,method = RequestMethod.POST)
	public String edit(@ModelAttribute("specialist")Specialist specialist,ModelMap modelMap,
					   @RequestParam("images") MultipartFile[] images,
					   @RequestParam("isIdCardImg") MultipartFile[] isIdCardImg,
					   @RequestParam("isHealthCertificateImg") MultipartFile isHealthCertificateImg,
					   @RequestParam("isMctCertificateImg") MultipartFile isMctCertificateImg,
					   @RequestParam("isNtCartificateImg") MultipartFile isNtCartificateImg,
					   @RequestParam("isGradeCartificateImg") MultipartFile isGradeCartificateImg,
					   @RequestParam("videoFile") MultipartFile videoFile){
		try {
			Specialist mm=null;
			if(specialist.getId()!=null){
				mm=specialistDao.findById(specialist.getId());
			}
			ExecuteResult<Map<String,Object>> result=null;
			if (images!=null && images.length>0&&images[0].getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(images,mm!=null?mm.getImageKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialist.setImageKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,specialist.getId());
					}
				}
			}
			if (isIdCardImg!=null && isIdCardImg.length>0 && isIdCardImg[0].getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(isIdCardImg,mm!=null?mm.getIdCardKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialist.setIdCardKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,specialist.getId());
					}
				}
			}
			if (isHealthCertificateImg!=null&&isHealthCertificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isHealthCertificateImg},mm!=null?mm.getHealthCertificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialist.setHealthCertificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,specialist.getId());
					}
				}
			}
			if (isMctCertificateImg!=null&&isMctCertificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isMctCertificateImg},mm!=null?mm.getMctCertificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialist.setMctCertificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,specialist.getId());
					}
				}
			}
			if (isNtCartificateImg!=null&&isNtCartificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isNtCartificateImg},mm!=null?mm.getNtCartificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialist.setNtCartificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,specialist.getId());
					}
				}
			}
			if (isGradeCartificateImg!=null&&isGradeCartificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isGradeCartificateImg},mm!=null?mm.getGradeCartificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialist.setGradeCartificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,specialist.getId());
					}
				}
			}
			if (videoFile!=null&&videoFile.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{videoFile},mm!=null?mm.getVideo():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							specialist.setVideo(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,specialist.getId());
					}
				}
			}
			specialistService.edit(specialist);
		} catch (Exception e) {
			e.printStackTrace();
			return toEdit(modelMap,null);
		}
		return "redirect:../common/success.do?reurl=specialist/page.do?explain=specialistdo";
	}
	/***批量上下架
	 * @param ids
	 * @param shelf:1-上架；0-下架
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "batchShelf", method = RequestMethod.POST)
	public ResponseEntity<ExecuteResult> batchShelf(@RequestParam("ids[]") Integer [] ids,@RequestParam("shelf") Integer shelf)throws Exception {
		ExecuteResult result  = new ExecuteResult<>();
		try {
			if (ids!= null&&ids.length>0) {
				specialistMapper.batchShelf(ids,shelf);
				result.setResultCode(ResultCode.SUCCESS);
			}else{
				result.setResultCode(ResultCode.FAIL);
				result.setMessage("请选择专家！");
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR);
			result.setMessage("系统出现异常请稍后再试！");
			e.printStackTrace();
		}finally {
			return new ResponseEntity<ExecuteResult>(result, HttpStatus.OK);
		}
	}
	/***通过手机号码查询用户是否存在
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkPhone", method = RequestMethod.GET)
	public ResponseEntity<ExecuteResult> checkPhone(@RequestParam("phone") String phone)throws Exception {
		ExecuteResult result  = new ExecuteResult<>();
		try {
			Specialist specialist=specialistDao.findByPhone(phone);
			if (specialist == null) {
				result.setResultCode(ResultCode.SUCCESS);
			}else{
				result.setResultCode(ResultCode.FAIL);
				result.setResult(specialist);
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.FAIL);
			e.printStackTrace();
		}finally {
			return new ResponseEntity<ExecuteResult>(result, HttpStatus.OK);
		}
	}
}
