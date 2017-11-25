package com.itee.bingsheng.dao;

import com.itee.bingsheng.entity.KoclaOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface KoclaOrderInfoDao extends JpaRepository<KoclaOrderInfo, Integer>, JpaSpecificationExecutor<KoclaOrderInfo> {


    @Modifying
    @Query("update KoclaOrderInfo o set o.statusId = ?2 where o.orderId = ?1")
    int updateStatus(int orderId, int statusId);

}
