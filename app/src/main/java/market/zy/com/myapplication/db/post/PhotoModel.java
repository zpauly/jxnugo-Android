package market.zy.com.myapplication.db.post;

import org.litepal.crud.DataSupport;

/**
 * Created by zpauly on 16-5-21.
 */
public class PhotoModel extends DataSupport {
    private String key;

    private int postId;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }
}
