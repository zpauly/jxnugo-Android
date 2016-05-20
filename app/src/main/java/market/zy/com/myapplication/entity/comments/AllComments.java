package market.zy.com.myapplication.entity.comments;

import java.util.List;

/**
 * Created by zpauly on 16-5-20.
 */
public class AllComments {
    /**
     * author : ruth86
     * authorAvatar : http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA
     * authorId : 40
     * body : 来评论
     * timestamp : 2016-05-12 01:34:45
     */

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
