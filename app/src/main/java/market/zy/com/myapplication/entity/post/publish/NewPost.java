package market.zy.com.myapplication.entity.post.publish;

import java.util.List;

import market.zy.com.myapplication.entity.post.PhotoKey;

/**
 * Created by zpauly on 16-5-22.
 */
public class NewPost {
    /**
     * userId : 1
     * body : 二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，
     * goodName : 戴尔灵越5537
     * goodNum : 1
     * goodPrice : 2000
     * goodLocation : 一栋N204
     * goodQuality : 7成新
     * goodBuyTime : 2014年6月
     * goodTag : 1
     * contact : 13361640744
     * photos : [{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"}]
     */

    private String userId;
    private String body;
    private String goodName;
    private int goodNum;
    private double goodPrice;
    private String goodLocation;
    private String goodQuality;
    private String goodBuyTime;
    private int goodTag;
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

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodLocation() {
        return goodLocation;
    }

    public void setGoodLocation(String goodLocation) {
        this.goodLocation = goodLocation;
    }

    public String getGoodQuality() {
        return goodQuality;
    }

    public void setGoodQuality(String goodQuality) {
        this.goodQuality = goodQuality;
    }

    public String getGoodBuyTime() {
        return goodBuyTime;
    }

    public void setGoodBuyTime(String goodBuyTime) {
        this.goodBuyTime = goodBuyTime;
    }

    public int getGoodTag() {
        return goodTag;
    }

    public void setGoodTag(int goodTag) {
        this.goodTag = goodTag;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
