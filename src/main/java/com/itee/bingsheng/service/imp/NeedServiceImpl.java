package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.dao.NeedDao;
import com.itee.bingsheng.service.NeedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class NeedServiceImpl implements NeedService {
	@Resource
	private NeedDao needDao;
	@Override
	public int updateState(Integer state, Integer id) throws Exception {
		return needDao.updateState(state,id);
	}
}