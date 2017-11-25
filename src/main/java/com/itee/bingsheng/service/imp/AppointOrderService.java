package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.mybatis.dao.AppointOrderDao;
import com.itee.bingsheng.service.IAppointOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Transactional
@Service
public class AppointOrderService implements IAppointOrderService {

	@Resource
	private AppointOrderDao dao;


	@Override
	public List<Map<String,Object>> getAppointOrderList(Map<String, Object> map) throws Exception{
		return dao.getAppointOrderList(map);
	}

	@Override
	public int getAppointOrderCount(Map<String, Object> map) throws Exception{
		return dao.getAppointOrderCount(map);
	}



}
