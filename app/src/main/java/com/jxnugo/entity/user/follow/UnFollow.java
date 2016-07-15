package com.jxnugo.entity.user.follow;

/**
 * Created by zpauly on 16-5-29.
 */
public class UnFollow {
    /**
     * userId : 101
     * unfollowedId : 58
     */

    private int userId;
    private int unfollowedId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUnfollowedId() {
        return unfollowedId;
    }

    public void setUnfollowedId(int unfollowedId) {
        this.unfollowedId = unfollowedId;
    }
}
