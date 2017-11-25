package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.entity.Recommend;
import com.itee.bingsheng.mybatis.dao.RecommendDao;
import com.itee.bingsheng.service.IRecommendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class RecommendService implements IRecommendService {

    @Resource
    RecommendDao dao;

    /**
     * 获取用户信息
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryAllData(Map<String, Object> map)throws Exception{
        return dao.queryAllData(map);
    }

    /**
     * 获取用户记录总数
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int getAllDataCount(Map<String, Object> map)throws Exception{
        return dao.getAllDataCount(map);
    }

    /**
     * 更新数据
     * @return
     * @throws Exception
     */
    @Override
    public int updateData(Map<String, Object> map)throws Exception{
        return dao.updateData(map);
    }



    /**
     * 保存推荐人信息
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int saveData(Map<String, Object> map)throws Exception{
        return dao.saveData(map);
    }

    /**
     * 通过编号获取id
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Recommend getDateById(int id)throws Exception{
        return dao.getDateById(id);
    }
}
