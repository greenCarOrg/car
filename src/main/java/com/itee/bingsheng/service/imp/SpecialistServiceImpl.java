package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.dao.MmServiceDao;
import com.itee.bingsheng.dao.SpecialistDao;
import com.itee.bingsheng.entity.MmService;
import com.itee.bingsheng.entity.Specialist;
import com.itee.bingsheng.service.SpecialistService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SpecialistServiceImpl implements SpecialistService {
	@Resource
	private SpecialistDao specialistDao;
	@Resource
	private MmServiceDao mmServiceDao;

	@Override
	public void edit(Specialist specialist) throws Exception {
		if(specialist.getId() == null){
			specialistDao.save(specialist);
		}else{
			Specialist mm=specialistDao.findById(specialist.getId());
			mm.setProvince(specialist.getProvince());
			mm.setCity(specialist.getCity());
			mm.setDistrict(specialist.getDistrict());
			mm.setIdCard(specialist.getIdCard());
			mm.setName(specialist.getName());
			mm.setPhone(specialist.getPhone());
			mm.setRefer(specialist.getRefer());
			mm.setRemark(specialist.getRemark());
			mm.setAffiliatedHospital(specialist.getAffiliatedHospital());
			mm.setReputation(specialist.getReputation());
			mm.setVideo(specialist.getVideo());
			mm.setIsIdCard(specialist.getIsIdCard());
			mm.setIsHealthCertificate(specialist.getIsHealthCertificate());
			mm.setIsMctCertificate(specialist.getIsMctCertificate());
			mm.setIsNtCartificate(specialist.getIsNtCartificate());
			mm.setIsGradeCartificate(specialist.getIsGradeCartificate());
			mm.setSummary(specialist.getSummary());
			mm.setAge(specialist.getAge());

			if(StringUtils.isNotEmpty(specialist.getIdCardKey())){
				mm.setIdCardKey(specialist.getIdCardKey());
			}
			if(StringUtils.isNotEmpty(specialist.getHealthCertificateKey())){
				mm.setHealthCertificateKey(specialist.getHealthCertificateKey());
			}
			if(StringUtils.isNotEmpty(specialist.getMctCertificateKey())){
				mm.setMctCertificateKey(specialist.getMctCertificateKey());
			}
			if(StringUtils.isNotEmpty(specialist.getNtCartificateKey())){
				mm.setNtCartificateKey(specialist.getNtCartificateKey());
			}
			if(StringUtils.isNotEmpty(specialist.getGradeCartificateKey())){
				mm.setGradeCartificateKey(specialist.getGradeCartificateKey());
			}
			if(StringUtils.isNotEmpty(specialist.getImageKey())){
				mm.setImageKey(specialist.getImageKey());
			}

			specialistDao.save(mm);
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