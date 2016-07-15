package com.jxnugo.entity.user.follow;

import java.util.List;

/**
 * Created by zpauly on 16-5-27.
 */
public class UserFollowed {
    private List<Follower> followed;

    public void setFollowed(List<Follower> followed) {
        this.followed = followed;
    }

    public List<Follower> getFollowed() {
        return followed;
    }
}
