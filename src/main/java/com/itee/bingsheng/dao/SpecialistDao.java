package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SpecialistDao extends JpaRepository<Specialist, Integer>, JpaSpecificationExecutor<Specialist> {
	Specialist findById(Integer id);
	Specialist findByPhone(String phone);
}
