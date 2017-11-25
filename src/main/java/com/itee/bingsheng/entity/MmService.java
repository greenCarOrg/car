package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2017/11/7.
 */
@Entity
@Table(name = "mm_service")
@DynamicInsert
@DynamicUpdate
public class MmService implements java.io.Serializable {
	private Integer id;
	private Integer mmId;
	private Integer serviceId;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMmId() {
		return mmId;
	}

	public void setMmId(Integer mmId) {
		this.mmId = mmId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
}
