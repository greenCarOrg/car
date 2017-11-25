package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2017/11/8.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "need")
public class Need {
	private Integer id;
	private Integer regionId;
	private Timestamp startTime;
	private Timestamp endTime;
	private String level;
	private String remark;
	private Integer state;
	private Integer userId;
	private Timestamp createTime;

	public void setId(int id) {
		this.id = id;
	}

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
	@Column(name = "region_id", nullable = true)
	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	@Basic
	@Column(name = "start_time", nullable = true)
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Basic
	@Column(name = "end_time", nullable = true)
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Basic
	@Column(name = "level", nullable = true, length = 30)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Basic
	@Column(name = "remark", nullable = true, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	@Column(name = "user_id", nullable = true)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Need need = (Need) o;

		if (id != need.id) return false;
		if (regionId != null ? !regionId.equals(need.regionId) : need.regionId != null) return false;
		if (startTime != null ? !startTime.equals(need.startTime) : need.startTime != null) return false;
		if (endTime != null ? !endTime.equals(need.endTime) : need.endTime != null) return false;
		if (level != null ? !level.equals(need.level) : need.level != null) return false;
		if (remark != null ? !remark.equals(need.remark) : need.remark != null) return false;
		if (state != null ? !state.equals(need.state) : need.state != null) return false;
		if (userId != null ? !userId.equals(need.userId) : need.userId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
		result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
		result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
		result = 31 * result + (level != null ? level.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		return result;
	}

	@Basic
	@Column(name = "create_time", nullable = true)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
