package com.itee.bingsheng.service;

import java.util.List;
import java.util.Map;

public interface IComboboxService {


    List<Map<String,Object>> getLoginLogList(Map<String,Object> map) throws Exception;

    int getLoginLogCount(Map<String,Object> map) throws Exception;



    List<Map<String,Object>> getSmsLogList(Map<String,Object> map) throws Exception;



    int getSmsLogCount(Map<String,Object> map) throws Exception;


    /**
     * 获取所有combobox
     * @param params
     * @return
     */
    List<Map<String,Object>> getComboxData(Map<String, Object> params);
    /**
     * 获取经纪人combobox
     * @param params
     * @return
     */
    List<Map<String,Object>> boxRecommend(Map<String, Object> params)throws Exception;

    /**
     * 根据名称获取常亮信息
     * @param code
     * @return
     * @throws Exception
     */
    double getCodeValueByCode(String code)throws Exception;
}
