package com.itee.bingsheng.service;


import com.itee.bingsheng.entity.Manufactor;

import java.util.List;
import java.util.Map;


public interface IManufactorService {

   	int saveManufactor(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryAllManufactor(Map<String, Object> map)throws Exception;

	int updateManufactor(Map<String, Object> map)throws Exception;

	int getAllCount(Map<String, Object> map)throws Exception;

	Manufactor getManfactorById(int id)throws Exception;

	/**
	 * 获取有效的厂家列表
	 * @param type 1百分百 2百分比   0就是全部
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> queryValidManufactor(Integer type)throws Exception;
}
