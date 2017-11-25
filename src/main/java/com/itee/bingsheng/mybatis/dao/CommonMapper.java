package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by abc on 2016/12/22.
 */
public interface CommonMapper extends BaseMapper{
	/**公共软删除方法
	 * @param tn：table名称
	 * @param state：状态(0-删除；1-有效)
	 * @param id：主键id
	 */
	void softDelete(@Param("tn") String tn, @Param("state")int state, @Param("id")Integer id);

	/**公共批量上下架方法
	 * @param tn：table名称
	 * @param ids:月嫂id数组
	 * @param shelf：1-上架；0-下架
	 * @throws Exception
	 */
	void batchShelf(@Param("tn") String tn,@Param("ids")Integer[] ids,@Param("shelf")Integer shelf)throws Exception;
}
