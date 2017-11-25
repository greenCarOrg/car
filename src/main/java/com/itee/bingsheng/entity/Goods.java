package com.itee.bingsheng.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "goods")
public class Goods implements java.io.Serializable{

    private int goodsId;
    private int catId;
    private String catName;
    private Integer extendCatId;
    private String extendCatName;
    private String goodsSn;
    private String goodsName;
    private int clickCount;
    private String brandName;
    private int storeCount;
    private int commentCount;
    private int weight;
    private double dealerprice;
    private double price;
    private String keywords;
    private String goodsRemark;
    private String goodsContent;
    private String originalImg;
    private int isReal;
    private int isOnSale;
    private int isFreeShipping;
    private int sort;
    private int isRecommend;
    private int isNew;
    private int isHot;
    private String originalId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date onTime;

    private int createBy;
    private int updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate;
    private int giveIntegral;
    private int suppliersId;
    private int salesSum;
    private int promType;
    private String promName;
    private double commission;
    private int point;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, precision = 11, scale = 0)
    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public Integer getExtendCatId() {
        return extendCatId;
    }

    public void setExtendCatId(Integer extendCatId) {
        this.extendCatId = extendCatId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }


    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public int getIsReal() {
        return isReal;
    }

    public void setIsReal(int isReal) {
        this.isReal = isReal;
    }

    public int getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(int isOnSale) {
        this.isOnSale = isOnSale;
    }

    public int getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(int isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public Date getOnTime() {
        return onTime;
    }

    public void setOnTime(Date onTime) {
        this.onTime = onTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getGiveIntegral() {
        return giveIntegral;
    }

    public void setGiveIntegral(int giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    public int getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(int suppliersId) {
        this.suppliersId = suppliersId;
    }

    public int getSalesSum() {
        return salesSum;
    }

    public void setSalesSum(int salesSum) {
        this.salesSum = salesSum;
    }

    public int getPromType() {
        return promType;
    }

    public void setPromType(int promType) {
        this.promType = promType;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    @Transient
    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Transient
    public String getExtendCatName() {
        return extendCatName;
    }

    public void setExtendCatName(String extendCatName) {
        this.extendCatName = extendCatName;
    }

    @Transient
    public String getPromName() {
        return promName;
    }

    public void setPromName(String promName) {
        this.promName = promName;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public double getDealerprice() {
        return dealerprice;
    }

    public void setDealerprice(double dealerprice) {
        this.dealerprice = dealerprice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
