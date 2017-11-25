package com.itee.bingsheng.service;


import com.itee.bingsheng.entity.Cycle;

import java.util.List;
import java.util.Map;


public interface ICycleService {

	int saveCycle(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryAllCycle(Map<String, Object> map)throws Exception;

	int updateCycle(Map<String, Object> map)throws Exception;

	int getAllCount(Map<String, Object> map)throws Exception;

	Cycle getCycleById(int id)throws Exception;

	int deleteCycleById(int id)throws Exception;
}
