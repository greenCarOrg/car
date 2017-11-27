package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.dao.DriverDao;
import com.itee.bingsheng.dao.MmServiceDao;
import com.itee.bingsheng.entity.Driver;
import com.itee.bingsheng.entity.MmService;
import com.itee.bingsheng.service.DriverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {
	@Resource
	private DriverDao driverDao;
	@Resource
	private MmServiceDao mmServiceDao;

	@Override
	public void edit(Driver driver) throws Exception {
		if(driver.getId() == null){
			driverDao.save(driver);
		}else{
			Driver mm= driverDao.findById(driver.getId());
			mm.setProvince(driver.getProvince());
			mm.setCity(driver.getCity());
			mm.setDistrict(driver.getDistrict());
			mm.setName(driver.getName());
			mm.setPhone(driver.getPhone());
			mm.setType(driver.getType());
			mm.setAge(driver.getAge());
			mm.setSex(driver.getSex());
			mm.setIdcard(driver.getIdcard());
			mm.setAddress(driver.getAddress());
			driverDao.save(mm);
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