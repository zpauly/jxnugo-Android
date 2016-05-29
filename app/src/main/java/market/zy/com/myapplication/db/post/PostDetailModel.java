package market.zy.com.myapplication.db.post;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import market.zy.com.myapplication.entity.post.PhotoKey;

/**
 * Created by zpauly on 16-5-20.
 */
public class PostDetailModel extends DataSupport {
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
    private String goodsBuyTime;
    private String goodsLocation;
    private String goodsName;
    private double goodsPrice;
    private String goodsQuality;
    private int goodsTag;
    private String cover;
    private int postId;
    private String postUserAvatar;
    private String postNickName;
    private String timestamp;
    private String url;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

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
