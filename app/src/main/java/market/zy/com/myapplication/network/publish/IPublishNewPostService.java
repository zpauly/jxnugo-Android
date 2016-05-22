package market.zy.com.myapplication.network.publish;

import market.zy.com.myapplication.entity.publish.NewPost;
import market.zy.com.myapplication.entity.publish.PublishSuccess;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zpauly on 16-5-22.
 */
public interface IPublishNewPostService {
    @POST("new_post")
    Observable<PublishSuccess> postNewPost(@Body NewPost newPost);
}
