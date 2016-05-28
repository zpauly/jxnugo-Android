package market.zy.com.myapplication.entity.user.follow;

/**
 * Created by zpauly on 16-5-28.
 */
public class JudgeFollow {
    /**
     * userId : 101
     * followerId : 59
     */

    private String userId;
    private String followerId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
}
