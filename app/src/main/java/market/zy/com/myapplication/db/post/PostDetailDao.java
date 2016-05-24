package market.zy.com.myapplication.db.post;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

import market.zy.com.myapplication.entity.post.OneSimplePost;

/**
 * Created by zpauly on 16-5-20.
 */
public class PostDetailDao {
    public static boolean insertPostDetail(OneSimplePost oneSimplePost) {
        PostDetailBean postDetail = new PostDetailBean();
        postDetail.setAuthor(oneSimplePost.getAuthor());
        postDetail.setBody(oneSimplePost.getBody());
        postDetail.setCommentsCount(oneSimplePost.getCommentsCount());
        postDetail.setContact(oneSimplePost.getContact());
        postDetail.setGoodsBuyTime(oneSimplePost.getGoodsBuyTime());
        postDetail.setGoodsLocation(oneSimplePost.getGoodsLocation());
        postDetail.setGoodsName(oneSimplePost.getGoodsName());
        postDetail.setGoodsPrice(oneSimplePost.getGoodsPrice());
        postDetail.setGoodsQuality(oneSimplePost.getGoodsQuality());
        postDetail.setGoodsTag(oneSimplePost.getGoodsTag());
        postDetail.setCover(oneSimplePost.getPhotos().get(0).getKey());
        postDetail.setPostId(oneSimplePost.getPostId());
        postDetail.setPostUserAvatar(oneSimplePost.getPostUserAvatar());
        postDetail.setPostNickName(oneSimplePost.getPostNickName());
        postDetail.setTimestamp(oneSimplePost.getTimestamp());
        postDetail.setUrl(oneSimplePost.getUrl());
        return postDetail.save();
    }

    public static int deletePostDetail() {
        return DataSupport.deleteAll(PostDetailBean.class);
    }

    public static List<PostDetailBean> queryPostDetail(int postId) {
        return DataSupport.where("postid = ?", String.valueOf(postId)).find(PostDetailBean.class);
    }

    public static int updatePostDetail(ContentValues values) {
        return DataSupport.updateAll(PostDetailBean.class, values);
    }
}
