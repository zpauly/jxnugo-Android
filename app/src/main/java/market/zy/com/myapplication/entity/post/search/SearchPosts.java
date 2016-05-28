package market.zy.com.myapplication.entity.post.search;

import java.util.List;

import market.zy.com.myapplication.entity.post.OneSimplePost;

/**
 * Created by zpauly on 16-5-28.
 */
public class SearchPosts {
    private int count;
    private String next;
    private List<OneSimplePost> posts;
    private String prev;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setPosts(List<OneSimplePost> posts) {
        this.posts= posts;
    }

    public List<OneSimplePost> getPosts() {
        return posts;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getNext() {
        return next;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getPrev() {
        return prev;
    }
}
