package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2017/11/13.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "specialist_service", schema = "motherpoint")
public class SpecialistService {
	private Integer id;
	private String name;
	private Integer shelf;
	private String img;
	private Double price;
	private String detail;
	private Integer sysuserId;
	private Date createTime;
	private Integer state;
	private Integer sId;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name", nullable = true, length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "shelf", nullable = true)
	public Integer getShelf() {
		return shelf;
	}

	public void setShelf(Integer shelf) {
		this.shelf = shelf;
	}

	@Basic
	@Column(name = "img", nullable = true, length = 32)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Basic
	@Column(name = "price", nullable = true, precision = 2)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Basic
	@Column(name = "detail", nullable = true, length = 1000)
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Basic
	@Column(name = "sysuser_id", nullable = true)
	public Integer getSysuserId() {
		return sysuserId;
	}

	public void setSysuserId(Integer sysuserId) {
		this.sysuserId = sysuserId;
	}

	@Basic
	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Basic
	@Column(name = "s_id", nullable = true)
	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
