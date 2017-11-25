package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.entity.GoodGroup;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.dao.GoodGroupMybatisDao;
import com.itee.bingsheng.service.IGoodGroupService;
import com.itee.bingsheng.web.WebConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class GoodGroupService implements IGoodGroupService {

    @Resource
    private GoodGroupMybatisDao dao;


    @Override
    public List<Map<String,Object>> queryAllGroup(Map<String,Object> map)throws Exception{
        return dao.queryAllGroup(map);
    }


    @Override
    public int queryAllGroupCount(Map<String,Object> map)throws Exception{
        return dao.queryAllGroupCount(map);
    }


    @Override
    public List<Map<String,Object>> queryFirstGroupList()throws Exception{
        return dao.queryFirstGroupList();
    }



    @Override
    public int saveGoodGroup(Map<String,Object> map)throws Exception {
        return  dao.saveGoodGroup(map);
    }


    @Override
    public int updateGoodGroup(Map<String,Object>map)throws Exception{
        return dao.updateGoodGroup(map);
    }


    @Override
    public GoodGroup getGoodGroupById(int id)throws Exception{
        return dao.getGoodGroupById(id);
    }
}
