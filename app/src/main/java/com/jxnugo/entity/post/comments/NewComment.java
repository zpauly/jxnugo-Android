package com.jxnugo.entity.post.comments;

/**
 * Created by zpauly on 16-5-24.
 */
public class NewComment {
    /**
     * userId : 58
     * postId : 16
     * body : api来测试评论
     */

    private int userId;
    private int postId;
    private String body;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
