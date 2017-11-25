package com.itee.bingsheng.service;


import java.util.List;
import java.util.Map;


public interface IWithdrawService {

	List<Map<String, Object>> queryAllWithdraw(Map<String, Object> map)throws Exception;

	int updateWithDraw(Map<String, Object> map)throws Exception;

	int getAllCount(Map<String, Object> map)throws Exception;
}
