package market.zy.com.myapplication.network;

import market.zy.com.myapplication.entity.post.OnePagePost;
import market.zy.com.myapplication.entity.post.collection.CollectPost;
import market.zy.com.myapplication.entity.post.collection.CollectStates;
import market.zy.com.myapplication.entity.post.collection.CollectionPosts;
import market.zy.com.myapplication.entity.post.collection.JudgeCollectPost;
import market.zy.com.myapplication.entity.post.collection.JudgeCollectStates;
import market.zy.com.myapplication.entity.post.collection.UncollectPost;
import market.zy.com.myapplication.entity.post.collection.UncollectStates;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.post.comments.NewComment;
import market.zy.com.myapplication.entity.post.comments.NewCommentStates;
import market.zy.com.myapplication.entity.post.delete.DeletePost;
import market.zy.com.myapplication.entity.post.delete.DeleteStates;
import market.zy.com.myapplication.entity.post.publish.NewPost;
import market.zy.com.myapplication.entity.post.publish.PublishStates;
import market.zy.com.myapplication.entity.post.user.UserPosts;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.entity.user.amend.AmendStates;
import market.zy.com.myapplication.entity.user.amend.AmendUseInfo;
import market.zy.com.myapplication.entity.user.follow.Follow;
import market.zy.com.myapplication.entity.user.follow.FollowStates;
import market.zy.com.myapplication.entity.user.follow.JudgeFollowStates;
import market.zy.com.myapplication.entity.user.follow.UnfollowStates;
import market.zy.com.myapplication.entity.user.follow.UserFollowed;
import market.zy.com.myapplication.entity.user.follow.UserFollowers;
import market.zy.com.myapplication.entity.user.login.LoginTokenSuccess;
import market.zy.com.myapplication.entity.user.registe.RegisteStates;
import market.zy.com.myapplication.entity.user.registe.RegisterInfo;
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
     * 请求七牛云上传token
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
    Observable<AmendStates> amendUsetInfo(@Header("Authorization") String auth, @Body AmendUseInfo amendUseInfo);

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
    Observable<UnfollowStates> unfollowUser(@Header("Authorization") String auth, @Body Follow follow);

    /**
     * 判断用户是否已经关注了某个用户
     * @param auth
     * @param follow
     * @return
     */
    @POST("judge_follow")
    Observable<JudgeFollowStates> judgeFollowUser(@Header("Authorization") String auth, @Body Follow follow);

    /**
     * 删除帖子
     * @param auth
     * @param post
     * @return
     */
    @POST("delete_post")
    Observable<DeleteStates> deletePost(@Header("Authorization") String auth, @Body DeletePost post);
}
