package com.jxnugo.entity.user.follow;

/**
 * Created by zpauly on 16-5-28.
 */
public class Follow {
    /**
     * userId : 101
     * followedId : 58
     */

    private int userId;
    private int followedId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowedId() {
        return followedId;
    }

    public void setFollowedId(int followedId) {
        this.followedId = followedId;
    }
}
