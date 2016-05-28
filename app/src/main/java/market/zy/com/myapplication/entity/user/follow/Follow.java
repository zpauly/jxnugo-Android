package market.zy.com.myapplication.entity.user.follow;

/**
 * Created by zpauly on 16-5-28.
 */
public class Follow {
    /**
     * userId : 101
     * followedId : 58
     */

    private String userId;
    private String followedId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowedId() {
        return followedId;
    }

    public void setFollowedId(String followedId) {
        this.followedId = followedId;
    }
}
