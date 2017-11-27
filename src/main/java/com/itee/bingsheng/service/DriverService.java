package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.Driver;

/**
 * Created by Cole on 2016/6/1.
 *
 * @author Jesse
 * @apiNote 司机service
 */
public interface DriverService {
	/**编辑
	 * @param driver
	 * @throws Exception
	 */
	public void edit(Driver driver)throws Exception;
	/**
	 * @param serviceIds:服务id
	 * @param mmId：id
	 * @throws Exception
	 */
	void addService(Integer[] serviceIds,Integer mmId)throws Exception;
}
