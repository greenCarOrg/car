package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.Recommend;

import java.util.List;
import java.util.Map;


public interface IRecommendService {


    /**
     * 获取用户信息
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryAllData(Map<String, Object> map)throws Exception;

    /**
     * 获取用户记录总数
     * @param map
     * @return
     * @throws Exception
     */
    int getAllDataCount(Map<String, Object> map)throws Exception;

    /**
     * 更新数据
     * @return
     * @throws Exception
     */
     int updateData(Map<String, Object> map)throws Exception;


    /**
     * 保存推荐人信息
     * @param map
     * @return
     * @throws Exception
     */
    int saveData(Map<String, Object> map)throws Exception;

    /**
     * 通过编号获取id
     * @param id
     * @return
     * @throws Exception
     */
    Recommend getDateById(int id)throws Exception;
}
