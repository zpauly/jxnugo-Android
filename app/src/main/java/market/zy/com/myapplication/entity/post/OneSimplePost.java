package market.zy.com.myapplication.entity.post;

import java.util.List;

/**
 * Created by zpauly on 16-5-19.
 */
public class OneSimplePost {

    /**
     * author : http://127.0.0.1:5000/api/user/2
     * body : Pellentesque viverra pede ac diam. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.
     * commentsCount : 0
     * contact : 1144728202
     * goodBuyTime : 2016-04-26
     * goodLocation : 78 Anhalt Parkway
     * goodName : Rental & Leasing Services
     * goodPrice : 1239.12
     * goodQuality : 9成新
     * goodTag : 4
     * photos : null
     * postId : 1
     * postUserAvator : http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA95DBEE326B.png
     * postUserName : nicole71
     * timestamp : 2016-05-07 00:00:00
     * url : http://127.0.0.1:5000/api/posts/1
     */

    private String author;
    private String body;
    private int commentsCount;
    private String contact;
    private String goodBuyTime;
    private String goodLocation;
    private String goodName;
    private double goodPrice;
    private String goodQuality;
    private int goodTag;
    private List<PhotoKey> photos;
    private int postId;
    private String postUserAvator;
    private String postUserName;
    private String timestamp;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGoodBuyTime() {
        return goodBuyTime;
    }

    public void setGoodBuyTime(String goodBuyTime) {
        this.goodBuyTime = goodBuyTime;
    }

    public String getGoodLocation() {
        return goodLocation;
    }

    public void setGoodLocation(String goodLocation) {
        this.goodLocation = goodLocation;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodQuality() {
        return goodQuality;
    }

    public void setGoodQuality(String goodQuality) {
        this.goodQuality = goodQuality;
    }

    public int getGoodTag() {
        return goodTag;
    }

    public void setGoodTag(int goodTag) {
        this.goodTag = goodTag;
    }

    public List<PhotoKey> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoKey> photos) {
        this.photos = photos;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostUserAvator() {
        return postUserAvator;
    }

    public void setPostUserAvator(String postUserAvator) {
        this.postUserAvator = postUserAvator;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
