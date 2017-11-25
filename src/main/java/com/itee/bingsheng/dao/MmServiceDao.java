package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.MmService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface MmServiceDao extends JpaRepository<MmService, Integer>, JpaSpecificationExecutor<MmService> {
	@Modifying
	@Query("delete from MmService t where t.mmId= ?1")
	int deleteMmService(Integer mmId);
}
