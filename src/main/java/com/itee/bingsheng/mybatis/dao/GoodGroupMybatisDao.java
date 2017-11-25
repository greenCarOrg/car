package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.entity.GoodGroup;

import java.util.List;
import java.util.Map;

public interface GoodGroupMybatisDao {


    List<Map<String,Object>> queryAllGroup(Map<String,Object> map)throws Exception;


    int queryAllGroupCount(Map<String,Object> map)throws Exception;


    List<Map<String,Object>> queryFirstGroupList()throws Exception;


    int saveGoodGroup(Map<String,Object> map)throws Exception;


    int updateGoodGroup(Map<String,Object> map)throws Exception;

    GoodGroup getGoodGroupById(int id)throws Exception;
}
