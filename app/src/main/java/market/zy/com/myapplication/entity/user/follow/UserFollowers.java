package market.zy.com.myapplication.entity.user.follow;

import java.util.List;

/**
 * Created by zpauly on 16-5-27.
 */
public class UserFollowers {
    private List<Follower> followers;

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }

    public List<Follower> getFollowers() {
        return followers;
    }
}
