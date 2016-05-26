package market.zy.com.myapplication.entity.post.user;

import java.util.List;

import market.zy.com.myapplication.entity.post.OneSimplePost;

/**
 * Created by zpauly on 16-5-26.
 */
public class UserPosts {
    private List<OneSimplePost> userPosts;

    public void setUserPosts(List<OneSimplePost> userPosts) {
        this.userPosts = userPosts;
    }

    public List<OneSimplePost> getUserPosts() {
        return userPosts;
    }
}
