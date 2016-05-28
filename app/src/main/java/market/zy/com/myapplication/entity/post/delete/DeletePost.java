package market.zy.com.myapplication.entity.post.delete;

/**
 * Created by zpauly on 16-5-28.
 */
public class DeletePost {
    /**
     * userId : 123
     * postId : 12
     */

    private int userId;
    private int postId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
