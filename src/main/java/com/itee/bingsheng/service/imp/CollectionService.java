package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.mybatis.dao.CollectionDao;
import com.itee.bingsheng.service.ICollectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CollectionService implements ICollectionService {

	@Resource
	CollectionDao dao;
	@Override
	public int saveCollection (Map<String,Object> map)throws Exception{
		return dao.saveCollection(map);
	}

	@Override
	public List<Map<String, Object>> queryAllCollection(Map<String,Object> map)throws Exception{
		return dao.queryAllCollection(map);
	}

	@Override
	public int getAllCount(Map<String, Object> map)throws Exception{
		return dao.getAllCount(map);
	}

	@Override
	public int cancelCollection(Map<String, Object> map)throws Exception{
		return dao.cancelCollection(map);
	}
}
