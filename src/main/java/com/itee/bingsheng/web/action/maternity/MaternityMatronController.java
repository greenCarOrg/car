package com.itee.bingsheng.web.action.maternity;

import com.itee.bingsheng.dao.MaternityMatronDao;
import com.itee.bingsheng.entity.MaternityMatron;
import com.itee.bingsheng.mybatis.dao.MaternityMatronMapper;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ICommonService;
import com.itee.bingsheng.service.MaternityMatronService;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *@description:月嫂controller
 */
@Controller
@RequestMapping(value = "/maternity")
public class MaternityMatronController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(MaternityMatronController.class.getName());
	@Resource
	private MaternityMatronMapper maternityMatronMapper;
	@Resource
	private MaternityMatronDao maternityMatronDao;
	@Resource
	private MaternityMatronService maternityMatronService;
	@Resource
	private ICommonService commonService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "maternity", method = RequestMethod.GET)
	public String maternity() {
		return "maternity/maternity";
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
			pages.setContent(maternityMatronMapper.pageList(pageSpecification));
			pages.setTotalElements(maternityMatronMapper.pageCount(pageSpecification));
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
				MaternityMatron maternityMatron=maternityMatronDao.findById(id);
				if(StringUtils.isNotEmpty(maternityMatron.getImageKey())){
					String [] images=maternityMatron.getImageKey().split(",");
					modelMap.addAttribute("images",images);
				}
				if(StringUtils.isNotEmpty(maternityMatron.getIdCardKey())){
					String [] idCardImgs=maternityMatron.getIdCardKey().split(",");
					modelMap.addAttribute("idCardImgs",idCardImgs);
				}
				modelMap.addAttribute("entity",maternityMatron);
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
			return "maternity/editMaternity";
		}
	}

	/**
	 * 添加或编辑
	 * @return
	 */
	@RequestMapping(value = "edit" ,method = RequestMethod.POST)
	public String edit(@ModelAttribute("maternityMatron")MaternityMatron maternityMatron,ModelMap modelMap,
					   @RequestParam("images") MultipartFile[] images,
					   @RequestParam("isIdCardImg") MultipartFile[] isIdCardImg,
					   @RequestParam("isHealthCertificateImg") MultipartFile isHealthCertificateImg,
					   @RequestParam("isMctCertificateImg") MultipartFile isMctCertificateImg,
					   @RequestParam("isNtCartificateImg") MultipartFile isNtCartificateImg,
					   @RequestParam("isGradeCartificateImg") MultipartFile isGradeCartificateImg,
					   @RequestParam("serviceContent") MultipartFile serviceContent,
					   @RequestParam("videoFile") MultipartFile videoFile){
		try {
			MaternityMatron mm=null;
			if(maternityMatron.getId()!=null){
				mm=maternityMatronDao.findById(maternityMatron.getId());
			}
			ExecuteResult<Map<String,Object>> result=null;
			if (images!=null && images.length>0&&images[0].getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(images,mm!=null?mm.getImageKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setImageKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			if (isIdCardImg!=null && isIdCardImg.length>0 && isIdCardImg[0].getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(isIdCardImg,mm!=null?mm.getIdCardKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setIdCardKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			if (isHealthCertificateImg!=null&&isHealthCertificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isHealthCertificateImg},mm!=null?mm.getHealthCertificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setHealthCertificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			if (isMctCertificateImg!=null&&isMctCertificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isMctCertificateImg},mm!=null?mm.getMctCertificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setMctCertificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			if (isNtCartificateImg!=null&&isNtCartificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isNtCartificateImg},mm!=null?mm.getNtCartificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setNtCartificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			if (isGradeCartificateImg!=null&&isGradeCartificateImg.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{isGradeCartificateImg},mm!=null?mm.getGradeCartificateKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setGradeCartificateKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			if (serviceContent!=null&&serviceContent.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{serviceContent},mm!=null?mm.getServiceContentKey():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setServiceContentKey(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			if (videoFile!=null&&videoFile.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{videoFile},mm!=null?mm.getVideo():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							maternityMatron.setVideo(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,maternityMatron.getId());
					}
				}
			}
			maternityMatronService.edit(maternityMatron);
		} catch (Exception e) {
			e.printStackTrace();
			return toEdit(modelMap,null);
		}
		return "redirect:../common/success.do?reurl=maternity/maternity.do?explain=maternitydo";
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
			MaternityMatron maternityMatron=maternityMatronDao.findByPhone(phone);
			if (maternityMatron == null) {
				result.setResultCode(ResultCode.SUCCESS);
			}else{
				result.setResultCode(ResultCode.FAIL);
				result.setResult(maternityMatron);
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.FAIL);
			e.printStackTrace();
		}finally {
			return new ResponseEntity<ExecuteResult>(result, HttpStatus.OK);
		}
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
				maternityMatronMapper.batchShelf(ids,shelf);
				result.setResultCode(ResultCode.SUCCESS);
			}else{
				result.setResultCode(ResultCode.FAIL);
				result.setMessage("请选择月嫂或育婴师！");
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR);
			result.setMessage("系统出现异常请稍后再试！");
			e.printStackTrace();
		}finally {
			return new ResponseEntity<ExecuteResult>(result, HttpStatus.OK);
		}
	}
	/***获取服务
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMaternityService", method = RequestMethod.POST)
	public ResponseEntity<ExecuteResult> getMaternityService(@RequestParam("id") Integer id,@RequestParam("type") Integer type)throws Exception {
		ExecuteResult result  = new ExecuteResult<>();
		try {
			List<Map<String,Object>> list = maternityMatronMapper.getMaternityService(id,type);
			if (list!= null&&list.size()>0) {
				result.setResultCode(ResultCode.SUCCESS);
				result.setResult(list);
			}else{
				result.setResultCode(ResultCode.FAIL);
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.FAIL);
			e.printStackTrace();
		}finally {
			return new ResponseEntity<ExecuteResult>(result, HttpStatus.OK);
		}
	}
	/**
	 * 添加服务
	 * @return
	 */
	@RequestMapping(value = "addService" ,method = RequestMethod.POST)
	public ResponseEntity<ExecuteResult> addService(@RequestParam("serviceIds[]") Integer[] serviceIds,@RequestParam("mmId") Integer mmId){
		ExecuteResult result  = new ExecuteResult<>();
		try {
			if (serviceIds!= null&&serviceIds.length>0) {
				maternityMatronService.addService(serviceIds,mmId);
				result.setResultCode(ResultCode.SUCCESS);
			}else{
				result.setResultCode(ResultCode.FAIL);
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.FAIL);
			e.printStackTrace();
		}finally {
			return new ResponseEntity<ExecuteResult>(result, HttpStatus.OK);
		}
	}
}
