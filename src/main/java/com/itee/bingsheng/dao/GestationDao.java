package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.Gestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface GestationDao extends JpaRepository<Gestation, Integer>, JpaSpecificationExecutor<Gestation> {
	Gestation findById(Integer id);
}
