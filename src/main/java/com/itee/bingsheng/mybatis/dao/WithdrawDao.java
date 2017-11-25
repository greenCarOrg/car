package com.itee.bingsheng.mybatis.dao;

import java.util.List;
import java.util.Map;


public interface WithdrawDao {

    List<Map<String, Object>> queryAllWithdraw(Map<String, Object> map)throws Exception;

    int updateWithDraw(Map<String, Object> map)throws Exception;

    int getAllCount(Map<String, Object> map)throws Exception;

}
