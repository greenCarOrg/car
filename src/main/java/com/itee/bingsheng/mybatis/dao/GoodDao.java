package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.entity.Goods;

import java.util.List;
import java.util.Map;


public interface GoodDao {

    int saveGood(Goods goods)throws Exception;

    List<Map<String, Object>> queryGoodList(Map<String, Object> map)throws Exception;

    void updateGood(Goods goods)throws Exception;

    int queryGoodListCount(Map<String, Object> map)throws Exception;


    /**搜索商品信息
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> searchGoods(Map<String,Object> params)throws Exception;

    /**
     * 更新商品明细信息
     * @param detail
     * @return
     * @throws Exception
     */
    int updateGoodAttr(Map<String,Object> detail)throws Exception;

    /**获取商品
     * @param params
     * @return
     * @throws Exception
     */
    List<Goods> getGoodsList(Map<String,Object> params)throws Exception;

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
