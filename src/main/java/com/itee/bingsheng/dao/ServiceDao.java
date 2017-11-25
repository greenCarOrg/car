package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.TService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ServiceDao extends JpaRepository<TService, Integer>, JpaSpecificationExecutor<TService> {
	TService findById(Integer id);
	/**
	 * @param state:状态
	 * @param id
	 * @return
	 */
	@Modifying
	@Query("update TService t set t.state=?1 where t.id= ?2")
	int updateState(Integer state,Integer id);
}
