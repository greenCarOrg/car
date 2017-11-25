package com.itee.bingsheng.service;


import com.itee.bingsheng.entity.Goods;

import java.util.List;
import java.util.Map;


public interface IGoodService {

	int saveGood(Goods goods)throws Exception;

	/**
	 * 商品列表查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryGoodList(Map<String, Object> map)throws Exception;

	/**
	 * 商品查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryGoodInfo(Map<String, Object> map)throws Exception;

	/**
	 * 更新商品信息
	 * @param goods
	 * @return
	 * @throws Exception
	 */
	int updateGood(Goods goods)throws Exception;

	/**
	 * 商品列表查询总数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int queryGoodListCount(Map<String, Object> map)throws Exception;


	/**
	 * 查询商品属性
	 * @param map
	 * @return
	 * @throws Exception
	 */
	 Goods getGoodsByGoodsMap(Map<String, Object> map)throws Exception;


	/**
	 * 更新商品明细信息
	 * @return
	 * @throws Exception
	 */
	int updateGoodAttr(Map<String,Object> map)throws Exception;

	/**
	 * 获取商品的属性
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getGoodAttr(int goodsId)throws Exception;

	/**
	 * 保存商品属性
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	int saveGoodAttr(Map<String,Object> detail)throws Exception;


	/**
	 * 变更属性时更改购物车的商品状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int updateGoodCartState(Map<String,Object> map)throws Exception;
}
