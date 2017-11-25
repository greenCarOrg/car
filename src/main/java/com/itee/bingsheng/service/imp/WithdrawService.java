package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.mybatis.dao.WithdrawDao;
import com.itee.bingsheng.service.IWithdrawService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WithdrawService implements IWithdrawService {

    @Resource
    WithdrawDao dao;

    @Override
    public List<Map<String, Object>> queryAllWithdraw(Map<String,Object> map)throws Exception{
        return dao.queryAllWithdraw(map);
    }

    @Override
    public int updateWithDraw(Map<String,Object> map)throws Exception{
        return dao.updateWithDraw(map);
    }

    @Override
    public int getAllCount(Map<String,Object> map)throws Exception{
        return dao.getAllCount(map);
    }


}