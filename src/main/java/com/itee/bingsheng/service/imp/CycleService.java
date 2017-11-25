package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.entity.Cycle;
import com.itee.bingsheng.mybatis.dao.CycleDao;
import com.itee.bingsheng.service.ICycleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CycleService implements ICycleService {

    @Resource
    CycleDao dao;


    @Override
    public int saveCycle(Map<String, Object> map)throws Exception{
        return dao.saveCycle(map);
    }

    @Override
    public List<Map<String, Object>> queryAllCycle(Map<String, Object> map)throws Exception{
        return dao.queryAllCycle(map);
    }

    @Override
    public int updateCycle(Map<String, Object> map)throws Exception{
        return dao.updateCycle(map);
    }

    @Override
    public int getAllCount(Map<String, Object> map)throws Exception{
        return dao.getAllCount(map);
    }

    @Override
    public Cycle getCycleById(int id)throws Exception{
        return dao.getCycleById(id);
    }

    @Override
    public int deleteCycleById(int id)throws Exception{
        return dao.deleteCycleById(id);
    }
}