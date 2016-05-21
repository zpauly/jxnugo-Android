package market.zy.com.myapplication.network.post;

import market.zy.com.myapplication.entity.post.OnePagePost;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zpauly on 16-5-19.
 */
public interface IPagePostsService {
    @GET("posts")
    Observable<OnePagePost> getOnePagePost(@Header("Authorization") String auth, @Query("page") int pageId);
}
