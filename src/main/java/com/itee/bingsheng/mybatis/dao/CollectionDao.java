package com.itee.bingsheng.mybatis.dao;

import java.util.List;
import java.util.Map;


public interface CollectionDao {


	int saveCollection(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryAllCollection(Map<String, Object> map)throws Exception;

	int getAllCount(Map<String, Object> map)throws Exception;

	int cancelCollection(Map<String, Object> map)throws Exception;
}
