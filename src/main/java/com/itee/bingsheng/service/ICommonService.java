package com.itee.bingsheng.service;


import java.util.List;
import java.util.Map;

public interface ICommonService {

	/**
	 * 保存短信日志
	 * @param phoneNumber
	 * @param randCode
	 * @param returnmsg
	 * @param type
	 * @return
	 * @throws Exception
	 */
   	int saveSmsLog(String phoneNumber,String randCode,String returnmsg,int type)throws Exception;

	/**
	 * 获取省市级联
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getRegion(Map<String,Object> map)throws Exception;


	/**
	 * 获取用户地址
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getUserAddress(int userId)throws Exception;


	/**
	 * 设置用户常用收获地址
	 * @return
	 * @throws Exception
	 */
	int updateUserDefaultAddress(Map<String,Object> map)throws Exception;

	/**
	 * 新增用户收货地址
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int addUserAddress(Map<String,Object> map)throws Exception;

	/**
	 * 获取快递列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getShipping(Map<String,Object> map)throws Exception;

	/**
	 * 保存接口调用信息
	 * @param params
	 * @return
	 */
	int saveIntefaceLog(Map<String, Object> params)throws Exception;


	List<Map<String,Object>> getInterfaceLogList(Map<String, Object> params) throws Exception;

	int getInterfaceLogCount(Map<String, Object> params)throws Exception;
}
