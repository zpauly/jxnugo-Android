package market.zy.com.myapplication.entity.post;

import java.util.List;

/**
 * Created by zpauly on 16-5-19.
 */
public class OneSimplePost {
    /**
     * author : http://www.jxnugo.com/api/user/160
     * author_id : 160
     * body : Aenean sit amet justo. Vivamus vel nulla eget eros elementum pellentesque.
     * commentsCount : 0
     * contact : 1083007150
     * goodsBuyTime : 2016-05-01
     * goodsLocation : 9371 Roxbury Lane
     * goodsName : Manufactured Housing
     * goodsPrice : 1239.12
     * goodsQuality : 9成新
     * goodsTag : 4
     * photos : [{"key":"FsEAykjBT4VlaqS934nGjd7GbmLu"},{"key":"Fn5e3ZfrHJ4GbfPmlaAjhfYAxiFn"}]
     * postId : 2
     * postUserAvatar : http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA95DBEE326B.png
     * postNickName : carol89
     * timestamp : 2016-05-16 08:00:00
     * url : http://www.jxnugo.com/api/posts/2
     */

    private String author;
    private int author_id;
    private String body;
    private int commentsCount;
    private String contact;
    private String goodsBuyTime;
    private String goodsLocation;
    private String goodsName;
    private double goodsPrice;
    private String goodsQuality;
    private int goodsTag;
    private int postId;
    private String postUserAvatar;
    private String postNickName;
    private String timestamp;
    private String url;

    private List<PhotoKey> photos;

    public List<PhotoKey> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoKey> photoKs) {
        this.photos = photoKs;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
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

    public String getGoodsBuyTime() {
        return goodsBuyTime;
    }

    public void setGoodsBuyTime(String goodsBuyTime) {
        this.goodsBuyTime = goodsBuyTime;
    }

    public String getGoodsLocation() {
        return goodsLocation;
    }

    public void setGoodsLocation(String goodsLocation) {
        this.goodsLocation = goodsLocation;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsQuality() {
        return goodsQuality;
    }

    public void setGoodsQuality(String goodsQuality) {
        this.goodsQuality = goodsQuality;
    }

    public int getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(int goodsTag) {
        this.goodsTag = goodsTag;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostUserAvatar() {
        return postUserAvatar;
    }

    public void setPostUserAvatar(String postUserAvatar) {
        this.postUserAvatar = postUserAvatar;
    }

    public String getPostNickName() {
        return postNickName;
    }

    public void setPostNickName(String postNickName) {
        this.postNickName = postNickName;
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
