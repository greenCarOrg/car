package com.itee.bingsheng.mybatis.dao;

import java.util.List;
import java.util.Map;


public interface AppointOrderDao {

	List<Map<String,Object>> getAppointOrderList(Map<String, Object> map) throws Exception;

	int getAppointOrderCount(Map<String, Object> map) throws Exception;
}
