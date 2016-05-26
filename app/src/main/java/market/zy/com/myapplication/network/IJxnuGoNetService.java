package market.zy.com.myapplication.network;

import market.zy.com.myapplication.entity.post.OnePagePost;
import market.zy.com.myapplication.entity.post.collection.CollectionPosts;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.post.comments.NewComment;
import market.zy.com.myapplication.entity.post.comments.NewCommentSuccess;
import market.zy.com.myapplication.entity.post.publish.NewPost;
import market.zy.com.myapplication.entity.post.publish.PublishSuccess;
import market.zy.com.myapplication.entity.post.user.UserPosts;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.entity.user.amend.AmendSuccess;
import market.zy.com.myapplication.entity.user.amend.AmendUseInfo;
import market.zy.com.myapplication.entity.user.login.LoginTokenSuccess;
import market.zy.com.myapplication.entity.user.registe.RegisteSuccess;
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
    Observable<RegisteSuccess> registe(@Body RegisterInfo info);

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
    Observable<AmendSuccess> amendUsetInfo(@Header("Authorization") String auth, @Body AmendUseInfo amendUseInfo);

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
    Observable<PublishSuccess> postNewPost(@Header("Authorization") String auth, @Body NewPost newPost);

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
    Observable<NewCommentSuccess> addNewComment(@Header("Authorization") String auth, @Body NewComment newComment);

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
}
