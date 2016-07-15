package com.jxnugo.entity.user.follow;

/**
 * Created by zpauly on 16-5-28.
 */
public class JudgeFollow {
    /**
     * userId : 101
     * followerId : 59
     */

    private int userId;
    private int followerId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }
}
