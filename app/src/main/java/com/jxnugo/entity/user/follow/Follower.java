package com.jxnugo.entity.user.follow;

/**
 * Created by zpauly on 16-5-27.
 */
public class Follower {
    /**
     * aboutMe : Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est.
     * urlLinkUser : http://127.0.0.1:5000/api/user/37
     * userAvatar : http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA95DBEE326B.png
     * userId : 37
     * userName : nancy90
     */

    private String aboutMe;
    private String urlLinkUser;
    private String userAvatar;
    private int userId;
    private String userName;

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getUrlLinkUser() {
        return urlLinkUser;
    }

    public void setUrlLinkUser(String urlLinkUser) {
        this.urlLinkUser = urlLinkUser;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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
