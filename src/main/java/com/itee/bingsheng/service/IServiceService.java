package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.TService;

/**
 * Created by Cole on 2016/6/1.
 *
 * @author Jesse
 * @apiNote 服务service
 */
public interface IServiceService {
	/**编辑服务
	 * @param TService
	 * @throws Exception
	 */
	public void edit(TService TService)throws Exception;
	/**修改服务状态
	 *@description:
	 *@author:Jesse
	 *@date:2017-11-08 14:14:37
	 */
	int updateState(Integer state,Integer id)throws Exception;
}
