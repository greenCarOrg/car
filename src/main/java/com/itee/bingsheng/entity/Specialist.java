package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2017/11/9.
 */
@Entity
@Table(name = "specialist")
@DynamicInsert
@DynamicUpdate
public class Specialist {
	private Integer id;
	private String head;
	private String name;
	private Integer state;
	private Integer province;
	private Integer city;
	private Integer district;
	private String jwd;
	private String phone;
	private String idCard;
	private Double reputation;
	private String remark;
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
	private String summary;
	private String affiliatedHospital;
	private Integer sysuserId;
	private Timestamp createTime;
	private Integer age;
	private Integer shelf;

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
	@Column(name = "head", nullable = true, length = 255)
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
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
	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
	@Column(name = "jwd", nullable = true, length = 255)
	public String getJwd() {
		return jwd;
	}

	public void setJwd(String jwd) {
		this.jwd = jwd;
	}

	@Basic
	@Column(name = "phone", nullable = true, length = 11)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Basic
	@Column(name = "id_card", nullable = true, length = 20)
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Basic
	@Column(name = "reputation", nullable = true, precision = 2)
	public Double getReputation() {
		return reputation;
	}

	public void setReputation(Double reputation) {
		this.reputation = reputation;
	}

	@Basic
	@Column(name = "remark", nullable = true, length = 1000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Basic
	@Column(name = "refer", nullable = true)
	public Integer getRefer() {
		return refer;
	}

	public void setRefer(Integer refer) {
		this.refer = refer;
	}

	@Basic
	@Column(name = "video", nullable = true, length = 255)
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Basic
	@Column(name = "is_id_card", nullable = true)
	public Integer getIsIdCard() {
		return isIdCard;
	}

	public void setIsIdCard(Integer isIdCard) {
		this.isIdCard = isIdCard;
	}

	@Basic
	@Column(name = "is_health_certificate", nullable = true)
	public Integer getIsHealthCertificate() {
		return isHealthCertificate;
	}

	public void setIsHealthCertificate(Integer isHealthCertificate) {
		this.isHealthCertificate = isHealthCertificate;
	}

	@Basic
	@Column(name = "is_mct_certificate", nullable = true)
	public Integer getIsMctCertificate() {
		return isMctCertificate;
	}

	public void setIsMctCertificate(Integer isMctCertificate) {
		this.isMctCertificate = isMctCertificate;
	}

	@Basic
	@Column(name = "is_nt_cartificate", nullable = true)
	public Integer getIsNtCartificate() {
		return isNtCartificate;
	}

	public void setIsNtCartificate(Integer isNtCartificate) {
		this.isNtCartificate = isNtCartificate;
	}

	@Basic
	@Column(name = "is_grade_cartificate", nullable = true)
	public Integer getIsGradeCartificate() {
		return isGradeCartificate;
	}

	public void setIsGradeCartificate(Integer isGradeCartificate) {
		this.isGradeCartificate = isGradeCartificate;
	}

	@Basic
	@Column(name = "id_card_key", nullable = true, length = 50)
	public String getIdCardKey() {
		return idCardKey;
	}

	public void setIdCardKey(String idCardKey) {
		this.idCardKey = idCardKey;
	}

	@Basic
	@Column(name = "health_certificate_key", nullable = true, length = 50)
	public String getHealthCertificateKey() {
		return healthCertificateKey;
	}

	public void setHealthCertificateKey(String healthCertificateKey) {
		this.healthCertificateKey = healthCertificateKey;
	}

	@Basic
	@Column(name = "mct_certificate_key", nullable = true, length = 50)
	public String getMctCertificateKey() {
		return mctCertificateKey;
	}

	public void setMctCertificateKey(String mctCertificateKey) {
		this.mctCertificateKey = mctCertificateKey;
	}

	@Basic
	@Column(name = "nt_cartificate_key", nullable = true, length = 50)
	public String getNtCartificateKey() {
		return ntCartificateKey;
	}

	public void setNtCartificateKey(String ntCartificateKey) {
		this.ntCartificateKey = ntCartificateKey;
	}

	@Basic
	@Column(name = "grade_cartificate_key", nullable = true, length = 50)
	public String getGradeCartificateKey() {
		return gradeCartificateKey;
	}

	public void setGradeCartificateKey(String gradeCartificateKey) {
		this.gradeCartificateKey = gradeCartificateKey;
	}

	@Basic
	@Column(name = "image_key", nullable = true, length = 500)
	public String getImageKey() {
		return imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}

	@Basic
	@Column(name = "summary", nullable = true, length = 500)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Basic
	@Column(name = "affiliated_hospital", nullable = true, length = 255)
	public String getAffiliatedHospital() {
		return affiliatedHospital;
	}

	public void setAffiliatedHospital(String affiliatedHospital) {
		this.affiliatedHospital = affiliatedHospital;
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
	@Column(name = "create_time", nullable = true)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
}
