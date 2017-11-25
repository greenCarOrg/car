package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.MaternityMatron;

/**
 * Created by Cole on 2016/6/1.
 *
 * @author Jesse
 * @apiNote 月嫂service
 */
public interface MaternityMatronService {
	/**编辑月嫂
	 * @param maternityMatron
	 * @throws Exception
	 */
	public void edit(MaternityMatron maternityMatron)throws Exception;
	/**
	 * @param serviceIds:服务id
	 * @param mmId：月嫂id
	 * @throws Exception
	 */
	void addService(Integer[] serviceIds,Integer mmId)throws Exception;
}
