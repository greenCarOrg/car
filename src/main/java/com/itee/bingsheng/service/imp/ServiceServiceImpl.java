package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.dao.ServiceDao;
import com.itee.bingsheng.entity.TService;
import com.itee.bingsheng.service.IServiceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ServiceServiceImpl implements IServiceService {
	@Resource
	private ServiceDao serviceDao;

	@Override
	public void edit(TService tService) throws Exception {
		if(tService.getId() == null){
			serviceDao.save(tService);
		}else{
			TService s=serviceDao.findById(tService.getId());
			s.setName(tService.getName());
			s.setType(tService.getType());
			s.setRemark(tService.getRemark());
			if(StringUtils.isNotEmpty(tService.getImgKey())){
				s.setImgKey(tService.getImgKey());
			}
			serviceDao.save(s);
		}
	}

	@Override
	public int updateState(Integer state, Integer id) throws Exception {
		return serviceDao.updateState(state,id);
	}
}