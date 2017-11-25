package com.itee.bingsheng.service;

import java.util.List;
import java.util.Map;

public interface IAppointOrderService {

    /**
     * 获取预约订单信息
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getAppointOrderList(Map<String, Object> map) throws Exception;

    int getAppointOrderCount(Map<String, Object> map) throws Exception;

}
