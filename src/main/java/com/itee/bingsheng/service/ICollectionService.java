package com.itee.bingsheng.service;


import java.util.List;
import java.util.Map;


public interface ICollectionService {

   	int saveCollection(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryAllCollection(Map<String, Object> map)throws Exception;

	int getAllCount(Map<String, Object> map)throws Exception;

	int cancelCollection(Map<String, Object> map)throws Exception;
}
