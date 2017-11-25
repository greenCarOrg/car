package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.dao.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by abc on 2016/12/22.
 */
public interface MaternityMatronMapper extends BaseMapper{
	/***获取服务
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getMaternityService(Integer id,Integer type)throws Exception;

	/**
	 * @param ids:月嫂id数组
	 * @param shelf：1-上架；0-下架
	 * @throws Exception
	 */
	void batchShelf(Integer[] ids,Integer shelf)throws Exception;
}
