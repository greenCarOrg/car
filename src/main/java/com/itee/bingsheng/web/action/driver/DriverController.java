package com.itee.bingsheng.web.action.driver;

import com.itee.bingsheng.dao.DriverDao;
import com.itee.bingsheng.entity.Driver;
import com.itee.bingsheng.mybatis.dao.DriverMapper;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.DriverService;
import com.itee.bingsheng.service.ICommonService;
import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
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
 *@description:司机controller
 */
@Controller
@RequestMapping(value = "/driver")
public class DriverController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(DriverController.class.getName());
	@Resource
	private DriverMapper driverMapper;
	@Resource
	private DriverDao driverDao;
	@Resource
	private DriverService driverService;
	@Resource
	private ICommonService commonService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "driver", method = RequestMethod.GET)
	public String driver() {
		return "driver/driver";
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
			pages.setContent(driverMapper.pageList(pageSpecification));
			pages.setTotalElements(driverMapper.pageCount(pageSpecification));
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
	public String toEdit(ModelMap modelMap,@RequestParam(value = "id",required = false) Long id){
		try {
			if (id!=null) {
				Driver driver= driverDao.findById(id);
				modelMap.addAttribute("entity",driver);
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
			return "driver/editMaternity";
		}
	}

	/**
	 * 添加或编辑
	 * @return
	 */
	@RequestMapping(value = "edit" ,method = RequestMethod.POST)
	public String edit(@ModelAttribute("driver")Driver driver,ModelMap modelMap,
					   @RequestParam("idcardImgZ") MultipartFile idcardImgZ,
					   @RequestParam("idcardImgF") MultipartFile idcardImgF){
		try {
			Driver mm=null;
			if(driver.getId()!=null){
				mm= driverDao.findById(driver.getId());
			}
			ExecuteResult<Map<String,Object>> result=null;
			if (idcardImgZ!=null &&  idcardImgZ.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{idcardImgZ},mm!=null?mm.getIdcardImgZ():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							driver.setIdcardImgZ(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,driver.getId());
					}
				}
			}
			if (idcardImgF!=null &&  idcardImgF.getSize()>0) {
				result=QiNiuXiuXiuUtil.uploadFiles(new MultipartFile[]{idcardImgF},mm!=null?mm.getIdcardImgF():null);
				if (result!= null) {
					if (result.getResultCode().equals(ResultCode.SUCCESS)) {
						Map<String,Object> resultMap=result.getResult();
						if (resultMap!=null&&resultMap.get("fileKeys") != null) {
							driver.setIdcardImgF(resultMap.get("fileKeys").toString());
						}
					}else{
						modelMap.addAttribute("message", result.getMessage());
						return toEdit(modelMap,driver.getId());
					}
				}
			}
			driverService.edit(driver);
		} catch (Exception e) {
			e.printStackTrace();
			return toEdit(modelMap,null);
		}
		return "redirect:../common/success.do?reurl=driver/driver.do?explain=driverdo";
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
			Driver driver= driverDao.findByPhone(phone);
			if (driver == null) {
				result.setResultCode(ResultCode.SUCCESS);
			}else{
				result.setResultCode(ResultCode.FAIL);
				result.setResult(driver);
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
				driverMapper.batchShelf(ids,shelf);
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
	/**
	 * 添加服务
	 * @return
	 */
	@RequestMapping(value = "addService" ,method = RequestMethod.POST)
	public ResponseEntity<ExecuteResult> addService(@RequestParam("serviceIds[]") Integer[] serviceIds,@RequestParam("mmId") Integer mmId){
		ExecuteResult result  = new ExecuteResult<>();
		try {
			if (serviceIds!= null&&serviceIds.length>0) {
				driverService.addService(serviceIds,mmId);
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
