package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.Need;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface NeedDao extends JpaRepository<Need, Integer>, JpaSpecificationExecutor<Need> {
	@Modifying
	@Query("update  Need t set t.state=?1 where t.userId= ?2")
	int updateState(Integer state,Integer userId);
}
