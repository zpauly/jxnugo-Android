package market.zy.com.myapplication.network.comments;

import market.zy.com.myapplication.entity.comments.AllComments;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zpauly on 16-5-20.
 */
public interface ICommentsService {

    @GET("comments/{id}")
    Observable<AllComments> getAllComments(@Header("Authorization") String auth,@Path("id") int postId);
}
