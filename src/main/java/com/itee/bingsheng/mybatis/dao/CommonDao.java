package com.itee.bingsheng.mybatis.dao;

import java.util.List;
import java.util.Map;


public interface CommonDao {

    /**
     * 保存短信日志
     * @param map
     * @return
     * @throws Exception
     */
    int saveSmsLog(Map<String, Object> map)throws Exception;

    /**
     * 获取省市区信息
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getRegion(Map<String,Object> map)throws Exception;

    /**
     * 获取用户常用地址
     * @param userId
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getUserAddress(int userId)throws Exception;
    /**
     * 获取用户常用地址
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> findUserAddress(Map<String,Object> map)throws Exception;

    /**
     * 设置用户常用收获地址
     * @param map
     * @return
     * @throws Exception
     */
    int updateUserDefaultAddress(Map<String,Object> map)throws Exception;

    /**
     * 设置用户常用收获地址时先把当前用户的其他地址变为常用地址
     * @return
     * @throws Exception
     */
    int updateUserDefaultAddressCancel(Map<String,Object> map)throws Exception;

    /**
     * 新增用户收货地址
     * @param map
     * @return
     * @throws Exception
     */
    int addUserAddress(Map<String,Object> map)throws Exception;
    /**
     * 获取快递列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getShipping(Map<String,Object> map)throws Exception;

    /**
     * 保存接口调用信息
     * @param params
     * @return
     */
    int saveIntefaceLog(Map<String, Object> params)throws Exception;


    List<Map<String,Object>> getInterfaceLogList(Map<String, Object> params) throws Exception;

    int getInterfaceLogCount(Map<String, Object> params)throws Exception;
}
