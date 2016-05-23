package market.zy.com.myapplication.network;

import market.zy.com.myapplication.entity.post.OnePagePost;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.post.publish.NewPost;
import market.zy.com.myapplication.entity.post.publish.PublishSuccess;
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
    @GET("user/{id}")
    Observable<UserBasicInfo> getUserInfo(@Header("Authorization") String auth, @Path("id") int id);

    @GET()
    Observable<UserBasicInfo> getOthersInfo(@Header("Authorization") String auth);

    @POST("register")
    Observable<RegisteSuccess> registe(@Body RegisterInfo info);

    @GET("get_token")
    Observable<LoginTokenSuccess> getLoginToken(@Header("Authorization") String auth, @Query("userName") String username, @Query("passWord") String password);

    @POST("update_userInfo")
    Observable<AmendSuccess> amendUsetInfo(@Header("Authorization") String auth, @Body AmendUseInfo amendUseInfo);

    @GET("comments/{id}")
    Observable<AllComments> getAllComments(@Header("Authorization") String auth, @Path("id") int postId);

    @POST("new_post")
    Observable<PublishSuccess> postNewPost(@Header("Authorization") String auth, @Body NewPost newPost);

    @GET("posts")
    Observable<OnePagePost> getOnePagePost(@Header("Authorization") String auth, @Query("page") int pageId);

    @GET("posts")
    Observable<OnePagePost> getOnePagePost(@Query("page") int pageId);
}
