package com.jxnugo.network;

import com.jxnugo.entity.post.OnePagePost;
import com.jxnugo.entity.post.collection.CollectPost;
import com.jxnugo.entity.post.collection.CollectStates;
import com.jxnugo.entity.post.collection.CollectionPosts;
import com.jxnugo.entity.post.collection.JudgeCollectPost;
import com.jxnugo.entity.post.collection.JudgeCollectStates;
import com.jxnugo.entity.post.collection.UncollectPost;
import com.jxnugo.entity.post.collection.UncollectStates;
import com.jxnugo.entity.post.comments.AllComments;
import com.jxnugo.entity.post.comments.NewComment;
import com.jxnugo.entity.post.comments.NewCommentStates;
import com.jxnugo.entity.post.delete.DeletePost;
import com.jxnugo.entity.post.delete.DeleteStates;
import com.jxnugo.entity.post.publish.NewPost;
import com.jxnugo.entity.post.publish.PublishStates;
import com.jxnugo.entity.post.search.SearchKeyWords;
import com.jxnugo.entity.post.user.UserPosts;
import com.jxnugo.entity.user.UserBasicInfo;
import com.jxnugo.entity.user.amend.AmendStates;
import com.jxnugo.entity.user.amend.AmendUserInfo;
import com.jxnugo.entity.user.follow.Follow;
import com.jxnugo.entity.user.follow.FollowStates;
import com.jxnugo.entity.user.follow.JudgeFollow;
import com.jxnugo.entity.user.follow.JudgeFollowStates;
import com.jxnugo.entity.user.follow.UnFollow;
import com.jxnugo.entity.user.follow.UnfollowStates;
import com.jxnugo.entity.user.follow.UserFollowed;
import com.jxnugo.entity.user.follow.UserFollowers;
import com.jxnugo.entity.user.login.LoginTokenSuccess;
import com.jxnugo.entity.user.registe.RegisteStates;
import com.jxnugo.entity.user.registe.RegisterInfo;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zpauly on 16-5-22.
 */
public interface IJxnuGoNetService {
    /**
     * 获取个人用户信息
     * @param auth
     * @param id
     * @return
     */
    @GET("user/{id}")
    Observable<UserBasicInfo> getUserInfo(@Header("Authorization") String auth, @Path("id") int id);

    /**
     * 用户注册
     * @param info
     * @return
     */
    @POST("register")
    Observable<RegisteStates> registe(@Body RegisterInfo info);

    /**
     * login
     * @param auth
     * @param username
     * @param password
     * @return
     */
    @GET("get_token")
    Observable<LoginTokenSuccess> getLoginToken(@Header("Authorization") String auth, @Query("userName") String username, @Query("passWord") String password);

    /**
     * 更新用户个人信息
     * @param auth
     * @param amendUseInfo
     * @return
     */
    @POST("update_userInfo")
    Observable<AmendStates> amendUsetInfo(@Header("Authorization") String auth, @Body AmendUserInfo amendUseInfo);

    /**
     * 获取某个帖子的评论
     * @param auth
     * @param postId
     * @return
     */
    @GET("comments/{id}")
    Observable<AllComments> getAllComments(@Header("Authorization") String auth, @Path("id") int postId);

    /**
     * 发表新的帖子
     * @param auth
     * @param newPost
     * @return
     */
    @POST("new_post")
    Observable<PublishStates> postNewPost(@Header("Authorization") String auth, @Body NewPost newPost);

    /**
     * 获取一个页面的帖子的信息
     * @param pageId
     * @return
     */
    @GET("posts")
    Observable<OnePagePost> getOnePagePost(@Query("page") int pageId);

    /**
     * 对帖子发表评论
     * @param auth
     * @param newComment
     * @return
     */
    @POST("new_comment")
    Observable<NewCommentStates> addNewComment(@Header("Authorization") String auth, @Body NewComment newComment);

    /**
     * 获取某个用户收藏的帖子
     * @param auth
     * @param userId
     * @return
     */
    @GET("user_collectionpost/{id}")
    Observable<CollectionPosts> getCollectionPosts(@Header("Authorization") String auth, @Path("id") int userId);

    /**
     * 获取某个用户发布的帖子
     * @param auth
     * @param userId
     * @return
     */
    @GET("user_posts/{id}")
    Observable<UserPosts> getUserPosts(@Header("Authorization") String auth, @Path("id") int userId);

    /**
     * 获取某个用户关注的人
     * @param auth
     * @param userId
     * @return
     */
    @GET("user_followed/{id}")
    Observable<UserFollowed> getUserFollowed(@Header("Authorization") String auth, @Path("id") int userId);

    /**
     * 获取某个用户的粉丝
     * @param auth
     * @param userId
     * @return
     */
    @GET("user_followers/{id}")
    Observable<UserFollowers> getUserFollowers(@Header("Authorization") String auth, @Path("id") int userId);

    /**
     * 收藏某篇帖子
     * @param auth
     * @param collectPost
     * @return
     */
    @POST("collect")
    Observable<CollectStates> collectOnePost(@Header("Authorization") String auth, @Body CollectPost collectPost);

    /**
     * 取消收藏某篇帖子
     * @param auth
     * @param uncollectPost
     * @return
     */
    @POST("uncollect")
    Observable<UncollectStates> uncollectPost(@Header("Authorization") String auth, @Body UncollectPost uncollectPost);

    /**
     * 判断用户是否已经收藏过某篇帖子
     * @param auth
     * @param judgeCollectPost
     * @return
     */
    @POST("judge_collect")
    Observable<JudgeCollectStates> judgeCollectPost(@Header("Authorization") String auth, @Body JudgeCollectPost judgeCollectPost);

    /**
     * 关注某个用户
     * @param auth
     * @param follow
     * @return
     */
    @POST("follow")
    Observable<FollowStates> followUser(@Header("Authorization") String auth, @Body Follow follow);

    /**
     * 取消关注
     * @param auth
     * @param follow
     * @return
     */
    @POST("unfollow")
    Observable<UnfollowStates> unfollowUser(@Header("Authorization") String auth, @Body UnFollow follow);

    /**
     * 判断用户是否已经关注了某个用户
     * @param auth
     * @param follow
     * @return
     */
    @POST("judge_follow")
    Observable<JudgeFollowStates> judgeFollowUser(@Header("Authorization") String auth, @Body JudgeFollow follow);

    /**
     * 删除帖子
     * @param auth
     * @param post
     * @return
     */
    @POST("delete_post")
    Observable<DeleteStates> deletePost(@Header("Authorization") String auth, @Body DeletePost post);

    /**
     * 根据物品标签查询相关的帖子
     * @param tagId
     * @param pageId
     * @return
     */
    @GET("post_category/{tag}")
    Observable<OnePagePost> getPostsByTag(@Path("tag") int tagId, @Query("page") int pageId);

    @POST("query_post")
    Observable<OnePagePost> searchPosts(@Header("Authorization") String auth, @Body SearchKeyWords keyWords);
}
