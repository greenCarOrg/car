package com.itee.bingsheng.service;

/**
 * Created by Cole on 2016/6/1.
 *
 * @author Jesse
 * @apiNote service
 */
public interface NeedService {
	/**修改服务状态
	 *@description:
	 *@author:Jesse
	 *@date:2017-11-08 14:14:37
	 */
	int updateState(Integer state, Integer id)throws Exception;
}
