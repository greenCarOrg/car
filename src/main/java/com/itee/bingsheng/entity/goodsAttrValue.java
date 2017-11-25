package com.itee.bingsheng.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "goods_attr_value")
public class goodsAttrValue implements java.io.Serializable{

    private int gavId;
    private int goodsId;
    private String color;
    private String size;
	private double price;
	private String describe;
	private String imgKey;
	private String imgName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crateTime;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, precision = 11, scale = 0)
	public int getGavId() {
		return gavId;
	}

	public void setGavId(int gavId) {
		this.gavId = gavId;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgKey() {
		return imgKey;
	}

	public void setImgKey(String imgKey) {
		this.imgKey = imgKey;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
