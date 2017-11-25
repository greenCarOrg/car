package com.itee.bingsheng.service;


import java.util.List;
import java.util.Map;

public interface IFeedbookService {


	/**
	 * 获取用户反馈列表
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getFeedbookList(Map<String,Object> map)throws Exception;


	/**
	 * 获取用户反馈数量
	 * @return
	 * @throws Exception
	 */
	int getFeedbookCount(Map<String, Object> map)throws Exception;

	/**
	 * 更新用户反馈
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int updateFeedbook(Map<String, Object> map)throws Exception;


	/**
	 * 前端微信用户反馈
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int submitFeedbook(Map<String, Object> map)throws Exception;
}
