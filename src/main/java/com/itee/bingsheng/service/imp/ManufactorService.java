package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.entity.Manufactor;
import com.itee.bingsheng.mybatis.dao.ManufactorDao;
import com.itee.bingsheng.service.IManufactorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ManufactorService implements IManufactorService {

    @Resource
    ManufactorDao dao;

    @Override
    public int saveManufactor (Map<String,Object> map)throws Exception{
        return dao.saveManufactor(map);
    }

    @Override
    public List<Map<String, Object>> queryAllManufactor(Map<String,Object> map)throws Exception{
        return dao.queryAllManufactor(map);
    }

    @Override
    public int updateManufactor(Map<String,Object> map)throws Exception{
        return dao.updateManufactor(map);
    }

    @Override
    public int getAllCount(Map<String,Object> map)throws Exception{
        return dao.getAllCount(map);
    }

    @Override
    public Manufactor getManfactorById(int id)throws Exception{
        return dao.getManfactorById(id);
    }

    /**
     * 获取有效的厂家列表
     * @param type 1百分百 2百分比 0全部厂家
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String,Object>> queryValidManufactor(Integer type)throws Exception{
        return dao.queryValidManufactor(type);
    }
}