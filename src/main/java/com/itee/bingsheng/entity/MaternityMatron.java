package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2017/11/1.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "maternity_matron")
public class MaternityMatron implements java.io.Serializable {
	private Integer id;
	private String head;
	private String name;
	private Integer state;
	private Integer level;
	private Integer province;
	private Integer city;
	private Integer district;
	private String jwd;
	private String phone;
	private String idCard;
	private Double reputation;
	private String remark;
	private BigDecimal price;
	private Integer type;
	private Integer refer;
	private String video;
	private Integer isIdCard;
	private Integer isHealthCertificate;
	private Integer isMctCertificate;
	private Integer isNtCartificate;
	private Integer isGradeCartificate;
	private String idCardKey;
	private String healthCertificateKey;
	private String mctCertificateKey;
	private String ntCartificateKey;
	private String gradeCartificateKey;
	private String imageKey;
	private Integer sysuserId;
	private Timestamp createTime;
	private String summary;
	private String nativePlace;
	private Integer age;
	private Integer shelf;
	private String serviceContentKey;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, precision = 12, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public String getJwd() {
		return jwd;
	}

	public void setJwd(String jwd) {
		this.jwd = jwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Double getReputation() {
		return reputation;
	}

	public void setReputation(Double reputation) {
		this.reputation = reputation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getRefer() {
		return refer;
	}

	public void setRefer(Integer refer) {
		this.refer = refer;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsIdCard() {
		return isIdCard;
	}

	public void setIsIdCard(Integer isIdCard) {
		this.isIdCard = isIdCard;
	}

	public Integer getIsHealthCertificate() {
		return isHealthCertificate;
	}

	public void setIsHealthCertificate(Integer isHealthCertificate) {
		this.isHealthCertificate = isHealthCertificate;
	}

	public Integer getIsMctCertificate() {
		return isMctCertificate;
	}

	public void setIsMctCertificate(Integer isMctCertificate) {
		this.isMctCertificate = isMctCertificate;
	}

	public Integer getIsNtCartificate() {
		return isNtCartificate;
	}

	public void setIsNtCartificate(Integer isNtCartificate) {
		this.isNtCartificate = isNtCartificate;
	}

	public Integer getIsGradeCartificate() {
		return isGradeCartificate;
	}

	public void setIsGradeCartificate(Integer isGradeCartificate) {
		this.isGradeCartificate = isGradeCartificate;
	}

	public String getIdCardKey() {
		return idCardKey;
	}

	public void setIdCardKey(String idCardKey) {
		this.idCardKey = idCardKey;
	}


	public String getHealthCertificateKey() {
		return healthCertificateKey;
	}

	public void setHealthCertificateKey(String healthCertificateKey) {
		this.healthCertificateKey = healthCertificateKey;
	}


	public String getMctCertificateKey() {
		return mctCertificateKey;
	}

	public void setMctCertificateKey(String mctCertificateKey) {
		this.mctCertificateKey = mctCertificateKey;
	}


	public String getNtCartificateKey() {
		return ntCartificateKey;
	}

	public void setNtCartificateKey(String ntCartificateKey) {
		this.ntCartificateKey = ntCartificateKey;
	}


	public String getGradeCartificateKey() {
		return gradeCartificateKey;
	}

	public void setGradeCartificateKey(String gradeCartificateKey) {
		this.gradeCartificateKey = gradeCartificateKey;
	}


	public String getImageKey() {
		return imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}

	public Integer getSysuserId() {
		return sysuserId;
	}

	public void setSysuserId(Integer sysuserId) {
		this.sysuserId = sysuserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getShelf() {
		return shelf;
	}

	public void setShelf(Integer shelf) {
		this.shelf = shelf;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getServiceContentKey() {
		return serviceContentKey;
	}

	public void setServiceContentKey(String serviceContentKey) {
		this.serviceContentKey = serviceContentKey;
	}
}
