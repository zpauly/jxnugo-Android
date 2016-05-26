package market.zy.com.myapplication.entity.post.collection;

import java.util.List;

import market.zy.com.myapplication.entity.post.OneSimplePost;

/**
 * Created by zpauly on 16-5-26.
 */
public class CollectionPosts {
    private List<OneSimplePost> collectionPost;

    public void setCollectionPost(List<OneSimplePost> collectionPost) {
        this.collectionPost = collectionPost;
    }

    public List<OneSimplePost> getCollectionPost() {
        return collectionPost;
    }
}
