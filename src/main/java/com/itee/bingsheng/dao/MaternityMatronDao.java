package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.MaternityMatron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface MaternityMatronDao extends JpaRepository<MaternityMatron, Integer>, JpaSpecificationExecutor<MaternityMatron> {
	MaternityMatron findById(Integer id);
	MaternityMatron findByPhone(String phone);
}
