package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.dao.MaternityMatronDao;
import com.itee.bingsheng.dao.MmServiceDao;
import com.itee.bingsheng.entity.MaternityMatron;
import com.itee.bingsheng.entity.MmService;
import com.itee.bingsheng.service.MaternityMatronService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class MaternityMatronServiceImpl implements MaternityMatronService {
	@Resource
	private MaternityMatronDao maternityMatronDao;
	@Resource
	private MmServiceDao mmServiceDao;

	@Override
	public void edit(MaternityMatron maternityMatron) throws Exception {
		if(maternityMatron.getId() == null){
			maternityMatronDao.save(maternityMatron);
		}else{
			MaternityMatron mm=maternityMatronDao.findById(maternityMatron.getId());
			mm.setProvince(maternityMatron.getProvince());
			mm.setCity(maternityMatron.getCity());
			mm.setDistrict(maternityMatron.getDistrict());
			mm.setJwd(maternityMatron.getJwd());
			mm.setIdCard(maternityMatron.getIdCard());
			mm.setLevel(maternityMatron.getLevel());
			mm.setName(maternityMatron.getName());
			mm.setPhone(maternityMatron.getPhone());
			mm.setRefer(maternityMatron.getRefer());
			mm.setRemark(maternityMatron.getRemark());
			mm.setPrice(maternityMatron.getPrice());
			mm.setType(maternityMatron.getType());
			mm.setVideo(maternityMatron.getVideo());
			mm.setReputation(maternityMatron.getReputation());
			mm.setIsIdCard(maternityMatron.getIsIdCard());
			mm.setIsHealthCertificate(maternityMatron.getIsHealthCertificate());
			mm.setIsMctCertificate(maternityMatron.getIsMctCertificate());
			mm.setIsNtCartificate(maternityMatron.getIsNtCartificate());
			mm.setIsGradeCartificate(maternityMatron.getIsGradeCartificate());
			mm.setSysuserId(maternityMatron.getSysuserId());
			mm.setSummary(maternityMatron.getSummary());
			mm.setAge(maternityMatron.getAge());
			mm.setNativePlace(maternityMatron.getNativePlace());

			if(StringUtils.isNotEmpty(maternityMatron.getIdCardKey())){
				mm.setIdCardKey(maternityMatron.getIdCardKey());
			}
			if(StringUtils.isNotEmpty(maternityMatron.getHealthCertificateKey())) {
				mm.setHealthCertificateKey(maternityMatron.getHealthCertificateKey());
			}
			if(StringUtils.isNotEmpty(maternityMatron.getMctCertificateKey())) {
				mm.setMctCertificateKey(maternityMatron.getMctCertificateKey());
			}
			if(StringUtils.isNotEmpty(maternityMatron.getNtCartificateKey())) {
				mm.setNtCartificateKey(maternityMatron.getNtCartificateKey());
			}
			if(StringUtils.isNotEmpty(maternityMatron.getGradeCartificateKey())) {
				mm.setGradeCartificateKey(maternityMatron.getGradeCartificateKey());
			}
			if(StringUtils.isNotEmpty(maternityMatron.getImageKey())) {
				mm.setImageKey(maternityMatron.getImageKey());
			}
			if(StringUtils.isNotEmpty(maternityMatron.getServiceContentKey())) {
				mm.setServiceContentKey(maternityMatron.getServiceContentKey());
			}
			maternityMatronDao.save(mm);
		}
	}
	@Override
	public void addService(Integer[] serviceIds, Integer mmId) throws Exception {
		//先删除原有的月嫂和服务之间的关系，再重新添加关系
		mmServiceDao.deleteMmService(mmId);
		MmService mmService;
		for(Integer serviceId:serviceIds){
			mmService=new MmService();
			mmService.setMmId(mmId);
			mmService.setServiceId(serviceId);
			mmServiceDao.save(mmService);
		}
	}
}