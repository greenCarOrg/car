package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2017/11/27.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "driver")
public class Driver {
	private Long id;
	private String name;
	private Integer age;
	private Integer sex;
	private Integer cooperateModel;
	private String idcardImgZ;
	private String idcardImgF;
	private String phone;
	private Date joinTime;
	private String plateNumber;
	private Integer matterDrawStatus;
	private Integer province;
	private Integer city;
	private Integer district;
	private Integer status;
	private Integer becomeSilent;
	private Long rentCompanyId;
	private Integer type;
	private Double starNum;
	private Double lat;
	private Double lng;
	private String imageUrl;
	private Integer drivingYears;
	private String drivingLicence;
	private String idcard;
	private String password;
	private Integer carType;
	private Date createTime;
	private Date updateLnglatTime;
	private Date startWorkTime;
	private String address;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name", nullable = true, length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "age", nullable = true)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Basic
	@Column(name = "sex", nullable = true)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Basic
	@Column(name = "cooperate_model", nullable = true)
	public Integer getCooperateModel() {
		return cooperateModel;
	}

	public void setCooperateModel(Integer cooperateModel) {
		this.cooperateModel = cooperateModel;
	}

	@Basic
	@Column(name = "idcard_img_z", nullable = true, length = 255)
	public String getIdcardImgZ() {
		return idcardImgZ;
	}

	public void setIdcardImgZ(String idcardImgZ) {
		this.idcardImgZ = idcardImgZ;
	}

	@Basic
	@Column(name = "idcard_img_f", nullable = true, length = 255)
	public String getIdcardImgF() {
		return idcardImgF;
	}

	public void setIdcardImgF(String idcardImgF) {
		this.idcardImgF = idcardImgF;
	}

	@Basic
	@Column(name = "phone", nullable = true, length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Basic
	@Column(name = "join_time", nullable = true)
	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	@Basic
	@Column(name = "plate_number", nullable = true, length = 20)
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Basic
	@Column(name = "matter_draw_status", nullable = true)
	public Integer getMatterDrawStatus() {
		return matterDrawStatus;
	}

	public void setMatterDrawStatus(Integer matterDrawStatus) {
		this.matterDrawStatus = matterDrawStatus;
	}

	@Basic
	@Column(name = "province", nullable = true)
	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	@Basic
	@Column(name = "city", nullable = true)
	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	@Basic
	@Column(name = "district", nullable = true)
	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	@Basic
	@Column(name = "status", nullable = true)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Basic
	@Column(name = "become_silent", nullable = true)
	public Integer getBecomeSilent() {
		return becomeSilent;
	}

	public void setBecomeSilent(Integer becomeSilent) {
		this.becomeSilent = becomeSilent;
	}

	@Basic
	@Column(name = "rent_company_id", nullable = true)
	public Long getRentCompanyId() {
		return rentCompanyId;
	}

	public void setRentCompanyId(Long rentCompanyId) {
		this.rentCompanyId = rentCompanyId;
	}

	@Basic
	@Column(name = "type", nullable = true)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Basic
	@Column(name = "star_num", nullable = true, precision = 1)
	public Double getStarNum() {
		return starNum;
	}

	public void setStarNum(Double starNum) {
		this.starNum = starNum;
	}

	@Basic
	@Column(name = "lat", nullable = true, precision = 6)
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Basic
	@Column(name = "lng", nullable = true, precision = 6)
	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	@Basic
	@Column(name = "image_url", nullable = true, length = 255)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Basic
	@Column(name = "driving_years", nullable = true)
	public Integer getDrivingYears() {
		return drivingYears;
	}

	public void setDrivingYears(Integer drivingYears) {
		this.drivingYears = drivingYears;
	}

	@Basic
	@Column(name = "driving_licence", nullable = true, length = 255)
	public String getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	@Basic
	@Column(name = "idcard", nullable = true, length = 18)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Basic
	@Column(name = "password", nullable = true, length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "car_type", nullable = true)
	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	@Basic
	@Column(name = "create_time", nullable = true)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "update_lnglat_time", nullable = true)
	public Date getUpdateLnglatTime() {
		return updateLnglatTime;
	}

	public void setUpdateLnglatTime(Date updateLnglatTime) {
		this.updateLnglatTime = updateLnglatTime;
	}

	@Basic
	@Column(name = "start_work_time", nullable = true)
	public Date getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(Date startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	@Basic
	@Column(name = "address", nullable = true, length = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Driver driver = (Driver) o;

		if (id != driver.id) return false;
		if (name != null ? !name.equals(driver.name) : driver.name != null) return false;
		if (age != null ? !age.equals(driver.age) : driver.age != null) return false;
		if (sex != null ? !sex.equals(driver.sex) : driver.sex != null) return false;
		if (cooperateModel != null ? !cooperateModel.equals(driver.cooperateModel) : driver.cooperateModel != null)
			return false;
		if (idcardImgZ != null ? !idcardImgZ.equals(driver.idcardImgZ) : driver.idcardImgZ != null) return false;
		if (idcardImgF != null ? !idcardImgF.equals(driver.idcardImgF) : driver.idcardImgF != null) return false;
		if (phone != null ? !phone.equals(driver.phone) : driver.phone != null) return false;
		if (joinTime != null ? !joinTime.equals(driver.joinTime) : driver.joinTime != null) return false;
		if (plateNumber != null ? !plateNumber.equals(driver.plateNumber) : driver.plateNumber != null) return false;
		if (matterDrawStatus != null ? !matterDrawStatus.equals(driver.matterDrawStatus) : driver.matterDrawStatus != null)
			return false;
		if (province != null ? !province.equals(driver.province) : driver.province != null) return false;
		if (city != null ? !city.equals(driver.city) : driver.city != null) return false;
		if (district != null ? !district.equals(driver.district) : driver.district != null) return false;
		if (status != null ? !status.equals(driver.status) : driver.status != null) return false;
		if (becomeSilent != null ? !becomeSilent.equals(driver.becomeSilent) : driver.becomeSilent != null)
			return false;
		if (rentCompanyId != null ? !rentCompanyId.equals(driver.rentCompanyId) : driver.rentCompanyId != null)
			return false;
		if (type != null ? !type.equals(driver.type) : driver.type != null) return false;
		if (starNum != null ? !starNum.equals(driver.starNum) : driver.starNum != null) return false;
		if (lat != null ? !lat.equals(driver.lat) : driver.lat != null) return false;
		if (lng != null ? !lng.equals(driver.lng) : driver.lng != null) return false;
		if (imageUrl != null ? !imageUrl.equals(driver.imageUrl) : driver.imageUrl != null) return false;
		if (drivingYears != null ? !drivingYears.equals(driver.drivingYears) : driver.drivingYears != null)
			return false;
		if (drivingLicence != null ? !drivingLicence.equals(driver.drivingLicence) : driver.drivingLicence != null)
			return false;
		if (idcard != null ? !idcard.equals(driver.idcard) : driver.idcard != null) return false;
		if (password != null ? !password.equals(driver.password) : driver.password != null) return false;
		if (carType != null ? !carType.equals(driver.carType) : driver.carType != null) return false;
		if (createTime != null ? !createTime.equals(driver.createTime) : driver.createTime != null) return false;
		if (updateLnglatTime != null ? !updateLnglatTime.equals(driver.updateLnglatTime) : driver.updateLnglatTime != null)
			return false;
		if (startWorkTime != null ? !startWorkTime.equals(driver.startWorkTime) : driver.startWorkTime != null)
			return false;
		if (address != null ? !address.equals(driver.address) : driver.address != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (age != null ? age.hashCode() : 0);
		result = 31 * result + (sex != null ? sex.hashCode() : 0);
		result = 31 * result + (cooperateModel != null ? cooperateModel.hashCode() : 0);
		result = 31 * result + (idcardImgZ != null ? idcardImgZ.hashCode() : 0);
		result = 31 * result + (idcardImgF != null ? idcardImgF.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (joinTime != null ? joinTime.hashCode() : 0);
		result = 31 * result + (plateNumber != null ? plateNumber.hashCode() : 0);
		result = 31 * result + (matterDrawStatus != null ? matterDrawStatus.hashCode() : 0);
		result = 31 * result + (province != null ? province.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (district != null ? district.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (becomeSilent != null ? becomeSilent.hashCode() : 0);
		result = 31 * result + (rentCompanyId != null ? rentCompanyId.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (starNum != null ? starNum.hashCode() : 0);
		result = 31 * result + (lat != null ? lat.hashCode() : 0);
		result = 31 * result + (lng != null ? lng.hashCode() : 0);
		result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
		result = 31 * result + (drivingYears != null ? drivingYears.hashCode() : 0);
		result = 31 * result + (drivingLicence != null ? drivingLicence.hashCode() : 0);
		result = 31 * result + (idcard != null ? idcard.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (carType != null ? carType.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateLnglatTime != null ? updateLnglatTime.hashCode() : 0);
		result = 31 * result + (startWorkTime != null ? startWorkTime.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		return result;
	}
}
