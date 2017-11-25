package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.dao.GestationDao;
import com.itee.bingsheng.entity.Gestation;
import com.itee.bingsheng.service.IGestationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GestationServiceImpl implements IGestationService {
	@Resource
	private GestationDao gestationDao;

	@Override
	public void edit(Gestation gestation) throws Exception {
			Gestation g=gestationDao.findById(gestation.getId());
			if (g == null) {
				g=new Gestation();
			}
			g.setContent(gestation.getContent());
			if(StringUtils.isNotEmpty(gestation.getImg())){
				g.setImg(gestation.getImg());
			}
			gestationDao.save(g);
	}
}