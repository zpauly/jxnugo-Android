package com.jxnugo.entity.post.publish;

import java.util.List;

import com.jxnugo.entity.post.PhotoKey;

/**
 * Created by zpauly on 16-5-22.
 */
public class NewPost {
    /**
     * userId : 1
     * body : 二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，
     * goodsName : 戴尔灵越5537
     * goodsNum : 1
     * goodsPrice : 2000
     * goodsLocation : 一栋N204
     * goodsQuality : 7成新
     * goodsBuyTime : 2014年6月
     * goodsTag : 1
     * contact : 13361640744
     * photos : [{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"}]
     */

    private String userId;
    private String body;
    private String goodsName;
    private int goodsNum;
    private double goodsPrice;
    private String goodsLocation;
    private String goodsQuality;
    private String goodsBuyTime;
    private int goodsTag;
    private String contact;

    private List<PhotoKey> photos;

    public void setPhotos(List<PhotoKey> photos) {
        this.photos = photos;
    }

    public List<PhotoKey> getPhotos() {
        return photos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodName) {
        this.goodsName = goodName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodNum) {
        this.goodsNum = goodNum;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodPrice) {
        this.goodsPrice = goodPrice;
    }

    public String getGoodsLocation() {
        return goodsLocation;
    }

    public void setGoodsLocation(String goodLocation) {
        this.goodsLocation = goodLocation;
    }

    public String getGoodsQuality() {
        return goodsQuality;
    }

    public void setGoodsQuality(String goodQuality) {
        this.goodsQuality = goodQuality;
    }

    public String getGoodsBuyTime() {
        return goodsBuyTime;
    }

    public void setGoodsBuyTime(String goodBuyTime) {
        this.goodsBuyTime = goodBuyTime;
    }

    public int getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(int goodTag) {
        this.goodsTag = goodTag;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
