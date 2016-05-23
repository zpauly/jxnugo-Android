package market.zy.com.myapplication.entity.post.comments;

/**
 * Created by zpauly on 16-5-10.
 */
public class Comment {
    /**
     * author : ruth86
     * authorAvatar : http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA
     * authorId : 40
     * body : 来评论
     * timestamp : 2016-05-12 01:34:45
     */

    private String author;
    private String authorAvatar;
    private int authorId;
    private String body;
    private String timestamp;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
