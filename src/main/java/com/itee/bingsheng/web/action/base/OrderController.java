package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.mybatis.dao.GoodDao;
import com.itee.bingsheng.mybatis.dao.OrderMDao;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.DataSourceResult;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.ICommonService;
import com.itee.bingsheng.utils.RandomUtil;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.utils.core.web.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *@description:订单controller
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends com.itee.bingsheng.web.action.BaseController{
	private Logger logger = Logger.getLogger(OrderController.class.getName());
	@Resource
	private OrderMDao orderMDao;
	@Resource
	private GoodDao goodDao;
	@Resource
	private ICommonService commonService;

	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "order", method = RequestMethod.GET)
	public String suppliers() {
		return "order/order";
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
			pages.setContent(orderMDao.pageList(pageSpecification));
			pages.setTotalElements(orderMDao.pageCount(pageSpecification));
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
				List<Map<String,Object>> entityList = orderMDao.findList(params);
				if (entityList!= null&&entityList.size()>0) {
					modelMap.put("entity",entityList.get(0));
				}
			}else{
				map.put("order_sn", RandomUtil.getOrderNo());
				modelMap.put("entity",map);
			}
			//获取省市区
			params.clear();
			params.put("level",1);
			modelMap.put("provinceList",commonService.getRegion(params));
			params.put("level",2);
			modelMap.put("cityList",commonService.getRegion(params));
			params.put("level",3);
			modelMap.put("districtList",commonService.getRegion(params));
			//获取快递列表
			modelMap.put("shippingList",commonService.getShipping(params));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "order/editOrder";
		}
	}

	/**
	 * 搜索商品信息
	 * @return
	 */
	@RequestMapping(value = "searchGoods" ,method = RequestMethod.POST)
	public @ResponseBody DataSourceResult searchGoods(@RequestBody PageSpecification<Map<String,Object>> pageSpecification){
		DataSourceResult result= null;
		try {
			result = new DataSourceResult();
			PageSpecification.FilterDescriptor filter=pageSpecification.getFilter();
			if (filter!= null&&filter.getFilters()!=null&& filter.getFilters().size()>0 && StringUtils.isNotEmpty(filter.getFilters().get(0).getField())) {
				params.put("goods_name",filter.getFilters().get(0).getValue());
			}
			result.setData(goodDao.searchGoods(params));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
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
			if(params.get("id") == null){
				orderMDao.add(params);
			}else{
				orderMDao.update(params);
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
			orderMDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return 1;
		}
	}
}
