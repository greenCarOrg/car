package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.dao.SsDao;
import com.itee.bingsheng.service.SsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class SsImpl implements SsService {
	@Resource
	private SsDao ssDao;

	@Override
	public void edit(com.itee.bingsheng.entity.SpecialistService ss) throws Exception {
		if(ss.getId() == null){
			ss.setCreateTime(new Date());
			ssDao.save(ss);
		}else{
			com.itee.bingsheng.entity.SpecialistService s=ssDao.findById(ss.getId());
			s.setName(ss.getName());
			if(StringUtils.isNotEmpty(ss.getImg())){
				s.setImg(ss.getImg());
			}
			s.setPrice(ss.getPrice());
			s.setDetail(ss.getDetail());
			s.setsId(ss.getsId());
			ssDao.save(s);
		}
	}
}