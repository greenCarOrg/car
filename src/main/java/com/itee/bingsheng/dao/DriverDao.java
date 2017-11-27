package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DriverDao extends JpaRepository<Driver, Integer>, JpaSpecificationExecutor<Driver> {
	Driver findById(Long id);
	Driver findByPhone(String phone);
}
