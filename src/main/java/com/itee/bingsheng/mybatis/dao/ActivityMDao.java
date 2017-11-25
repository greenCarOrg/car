package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.dao.BaseMapper;
import com.itee.bingsheng.persistence.PageSpecification;

import java.util.List;
import java.util.Map;

/**
 * Created by abc on 2016/12/22.
 */
public interface ActivityMDao extends BaseMapper{
	List<Map<String,Object>> pageList(PageSpecification<Map<String, Object>> pageSpecification)throws Exception;
	Long pageCount(PageSpecification<Map<String, Object>> pageSpecification)throws Exception;
	/**新增活动
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int add(Map<String, Object> params)throws Exception;
	/**编辑活动
	 * @param params
	 * @return
	 * @throws Exception
	 */
	void update(Map<String, Object> params)throws Exception;

	List<Map<String,Object>> findOne(Map<String, Object> params)throws Exception;

	/**删除活动
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(int id)throws Exception;
}
