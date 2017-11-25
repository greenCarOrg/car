package com.itee.bingsheng.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2017/11/17.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "gestation")
public class Gestation {
	private Integer id;
	private String content;
	private String img;

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
	@Column(name = "content", nullable = true, length = 2000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Basic
	@Column(name = "img", nullable = true, length = 40)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Gestation gestation = (Gestation) o;

		if (id != gestation.id) return false;
		if (content != null ? !content.equals(gestation.content) : gestation.content != null) return false;
		if (img != null ? !img.equals(gestation.img) : gestation.img != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + (img != null ? img.hashCode() : 0);
		return result;
	}
}
