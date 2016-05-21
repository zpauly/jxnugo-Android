package market.zy.com.myapplication.db.user;

import org.litepal.crud.DataSupport;

/**
 * Created by zpauly on 16-5-19.
 */
public class UserInfoBean extends DataSupport {
    /**
     * about_me : Nulla ac enim.
     * avatar : http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA
     * followed : 2
     * followers : 1
     * last_seen : 2016-05-12 12:02:57
     * location : San Clemente
     * member_since : 2016-05-09 00:00:00
     * name : Jane Lane
     * postCollectionCount : 4
     * contactMe : null
     * postCount : 1
     * sex : 男
     * userId : 40
     * userName : ruth86
     */

    private String about_me;
    private String avatar;
    private int followed;
    private int followers;
    private String last_seen;
    private String location;
    private String member_since;
    private String name;
    private int postCollectionCount;
    private String contactMe;
    private int postCount;
    private String sex;
    private int userId;
    private String userName;

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMember_since() {
        return member_since;
    }

    public void setMember_since(String member_since) {
        this.member_since = member_since;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostCollectionCount() {
        return postCollectionCount;
    }

    public void setPostCollectionCount(int postCollectionCount) {
        this.postCollectionCount = postCollectionCount;
    }

    public String getContactMe() {
        return contactMe;
    }

    public void setContactMe(String contactMe) {
        this.contactMe = contactMe;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
