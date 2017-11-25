package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.Specialist;

/**
 * Created by Cole on 2016/6/1.
 *
 * @author Jesse
 * @apiNote 专家service
 */
public interface SpecialistService {
	/**编辑
	 * @param specialist
	 * @throws Exception
	 */
	public void edit(Specialist specialist)throws Exception;
	/**
	 * @param serviceIds:服务id
	 * @param mmId：专家id
	 * @throws Exception
	 */
	void addService(Integer[] serviceIds, Integer mmId)throws Exception;
}
