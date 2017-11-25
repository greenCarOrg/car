package com.itee.bingsheng.web.action.base;

import com.alibaba.fastjson.JSONObject;
import com.itee.bingsheng.entity.Goods;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.dao.GoodDao;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.mybatis.util.Pager;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IComboboxService;
import com.itee.bingsheng.service.IGoodGroupService;
import com.itee.bingsheng.service.IGoodService;
import com.itee.bingsheng.utils.CreateUUIDUtil;
import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import com.itee.bingsheng.utils.RandomUtil;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/good")
public class GoodsController extends BaseController{

    @Resource
    private IGoodService goodService;
    @Resource
    private GoodDao goodDao;


	@Resource
	private IComboboxService comboboxService;

	@Resource
	private IGoodGroupService goodGroupService;


    /**
     * 管理端初始化查询商品
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping(value = "good", method = RequestMethod.GET)
    public String good(ModelMap modelMap,HttpServletRequest request) {
		Map maps = new HashMap();
		maps.put("type","lookUp");
		maps.put("name","IS_ENABLED");
		modelMap.put("validList",comboboxService.getComboxData(maps));
        return "goods/goodList";
    }


    /**
     * 管理端分页查询商品
     * @param pageRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "queryGoodList", method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage<Map<String,Object>> queryGoodList(@RequestBody PageSpecification<Map> pageRequest)throws Exception {
        MybatisPage<Map<String,Object>> pages = new MybatisPage<Map<String,Object>>();
        try{
			Map<String,Object> params = pageRequest.getData();
			BL3Utils.mapRemoveNull(params);
			params.put("pageSize", pageRequest.getPageSize());
            Pager pager = new Pager(pageRequest.getPage()-1,pageRequest.getTake());
            params.put("pager", pager);
            List<Map<String,Object>> list = goodService.queryGoodList(params);
            pages.setContent(list);
            pages.setTotalElements(goodService.queryGoodListCount(params));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

	/**
	 * 管理端商品上下架
	 * @param id
	 * @param isOnSale
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "enabledState", method = RequestMethod.GET)
	@ResponseBody
	public int enabledState(@RequestParam("id") int id, @RequestParam("isOnSale") int isOnSale)throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("goods_id",id);
		Map<String,Object> goodsMap = goodService.queryGoodInfo(map);
		Goods goods = goodService.getGoodsByGoodsMap(goodsMap);
		goods.setIsOnSale(isOnSale);
		return goodService.updateGood(goods);
	}


	/**
	 * 商品明细删除
	 * @param attrId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateGoodAttr", method = RequestMethod.GET)
	@ResponseBody
	public int updateGoodAttr(@RequestParam("attrId") int attrId,@RequestParam("state") int state,HttpServletRequest request)throws Exception {
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("attrId",attrId);
		map.put("state",state==1?0:1);

		//更新属性状态时变更购物车的状态
		goodService.updateGoodCartState(map);
		return goodService.updateGoodAttr(map);
	}


	/**
	 * 管理端初始化添加商品
	 * @param goods
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "toAddGood", method = RequestMethod.GET)
    public String toAddGood(@ModelAttribute("goods") Goods goods, ModelMap modelMap, HttpServletRequest request)throws Exception {
        Map maps = new HashMap();
        maps.put("type","lookUp");
        maps.put("name","IS_ENABLED");
        modelMap.put("enabledList",comboboxService.getComboxData(maps));
        //商品分组
		maps.clear();
		maps.put("type","goodGroup");
		maps.put("parentId","0");
		modelMap.put("goodGroupList",comboboxService.getComboxData(maps));
		// 商品扩展分组
		modelMap.put("extendGroupList",new ArrayList());
		//供货商
		maps.clear();
		maps.put("type","manufactor");

		modelMap.put("suppliers",comboboxService.getComboxData(maps));
		modelMap.put("goodsSn", RandomUtil.getOrderNo());
        return "goods/addGood";
    }

	/**
	 * 获取扩展分组
	 * @param parentId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getExtendCat", method = RequestMethod.GET)
	@ResponseBody
	public Object getExtendCat(@RequestParam("parentId")int parentId,HttpServletRequest request)throws Exception {
		Map<String ,Object> param=new HashMap<String, Object>();
		param.put("groupId",parentId);
		List<Map<String ,Object>> list=goodGroupService.queryAllGroup(param);
		return list;
	}


	/**
	 *
	 * 新增商品属性
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addGoodAttr", method = RequestMethod.POST)
	@ResponseBody
	public String addGoodAttr(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("code","success");
		Map<String,Object> map=request.getParameterMap();
		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file");// 获取上传文件名;
			if (file!=null && file.getSize()>0) {
				if(file.getBytes().length>1024*1024*200){
					ModelMap modelMap = new ModelMap();
					modelMap.addAttribute("message","file_to_big");
					return "file_to_big";
				}
				String fileKey = CreateUUIDUtil.createUUID();
				File upLoadFile = new File(file.getOriginalFilename());
				FileOutputStream outputStream = new FileOutputStream(upLoadFile);
				outputStream.write(file.getBytes());
				outputStream.flush();
				outputStream.close();
				String url=QiNiuXiuXiuUtil.uploadReturnURL(fileKey,upLoadFile);
				map.put("img",fileKey);
			}
			map.put("goodId",Integer.parseInt(request.getParameter("goodId")));
			map.put("attr",request.getParameter("attr"));
			map.put("num",Integer.parseInt(request.getParameter("num")));
			map.put("price",request.getParameter("price"));
			goodService.saveGoodAttr(map);

		}catch (Exception e) {
			e.printStackTrace();
			result.put("code","fail");
			result.put("message",e.getMessage());
		}
		return result.toString();
	}
    /**
     *
	 * 新增商品
     * @param goods
     * @param request
     * @return
     */
	@RequestMapping(value = "saveGood", method = RequestMethod.POST)
    public String saveGood(@ModelAttribute("goods") Goods goods,@RequestParam("myfiles") MultipartFile[] files,HttpServletRequest request)throws Exception {
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        try {
            File upLoadFile=null;
            if (files!=null && files.length>0) {
            	String fileKeys = "";
				String fileNames = "";

				for (MultipartFile file:files) {
					if (file!=null && file.getSize()>0) {
						if (file.getBytes().length > 1024 * 1024 * 200) {
							// return 0;  //200M 文件过大
							ModelMap modelMap = new ModelMap();
							modelMap.addAttribute("message", "file_to_big");
							return toAddGood(goods, modelMap, request);
						}
						String fileKey = CreateUUIDUtil.createUUID();
						upLoadFile = new File(file.getOriginalFilename());
						FileOutputStream outputStream = new FileOutputStream(upLoadFile);
						outputStream.write(file.getBytes());
						outputStream.flush();
						outputStream.close();
						String url = null;
						url = QiNiuXiuXiuUtil.uploadReturnURL(fileKey, upLoadFile);
						if (StringUtils.isEmpty(url)) {
							//  return 1;  //文件上传失败
							ModelMap modelMap = new ModelMap();
							modelMap.addAttribute("message", "file_up_fail");
							return toAddGood(goods, modelMap, request);
						}
						fileKeys += fileKey + ",";
						fileNames += file.getOriginalFilename() + ",";
					}
				}
				if(StringUtils.isNotEmpty(fileKeys)){
					fileKeys = fileKeys.substring(0,fileKeys.length()-1);
					fileNames = fileNames.substring(0,fileNames.length()-1);
				}
				goods.setOriginalId(fileKeys);
				goods.setOriginalImg(fileNames);
            }

            if(goods.getGoodsId()>0){
				goods.setUpdateBy(SysUser.getId());
				goodService.updateGood(goods);
			}else {
				goods.setCreateBy(SysUser.getId());
				goodService.saveGood(goods);
			}

        }catch (Exception e) {
            e.printStackTrace();
          //  return 2;  //系统异常
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("message","system_exception");
			return toAddGood(goods,modelMap,request);
        }
        return "redirect:../common/success.do?reurl=good/good.do?explain=gooddo";
    }


	/**
	 * 编辑修改商品信息
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "toEditGood", method = RequestMethod.GET)
    private String toEditGood(@RequestParam("id") int id,ModelMap modelMap)throws Exception {
    	Map<String,Object> params = new HashMap<String, Object>();
		params.put("goods_id",id);
		Goods goods=null;
		List<Goods> goodsList=goodDao.getGoodsList(params);
		if (goodsList!= null&&goodsList.size()>0) {
			goods=goodsList.get(0);
			modelMap.put("goods", goods);
			Map maps = new HashMap();
			maps.put("type","lookUp");
			maps.put("name","IS_ENABLED");
			modelMap.put("enabledList",comboboxService.getComboxData(maps));
			maps.clear();
			//商品分组
			maps.clear();
			maps.put("type","goodGroup");
			maps.put("parentId","0");
			modelMap.put("goodGroupList",comboboxService.getComboxData(maps));

			// 商品扩展分组
			maps.clear();
			maps.put("type","goodGroup");
			maps.put("parentId",goods.getCatId());
			modelMap.put("extendGroupList",comboboxService.getComboxData(maps));

			//供货商
			maps.clear();
			maps.put("type","manufactor");
			modelMap.put("suppliers",comboboxService.getComboxData(maps));
			// 标签list
			Map<String,Object> maps1 = new HashMap<String, Object>();
			if(goods.getExtendCatId()!=null){
				maps1.put("bind_uuid",goods.getCatId()+"_"+goods.getExtendCatId());
			}else {
				maps1.put("bind_uuid",goods.getCatId());
			}
			List<Map<String,Object>> attrList=goodService.getGoodAttr(id);
			modelMap.put("attrList",attrList);
		}
        return "goods/editGood";
    }


	/**
	 * 管理端初始化添加商品
	 * @param goodsId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "toEditGoodAttr", method = RequestMethod.GET)
	public String toEditGoodAttr(@RequestParam("goodsId") int goodsId, ModelMap modelMap) {
		try {
			List<Map<String,Object>> attrList=goodService.getGoodAttr(goodsId);
			modelMap.put("goodsId",goodsId);
			modelMap.put("attrList",attrList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "goods/editGoodAttr";
	}

}
