package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.SpecialistService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SsDao extends JpaRepository<SpecialistService, Integer>, JpaSpecificationExecutor<SpecialistService> {
	SpecialistService findById(Integer id);
}
