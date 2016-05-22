package market.zy.com.myapplication.network.publish;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.publish.NewPost;
import market.zy.com.myapplication.entity.publish.PublishSuccess;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-22.
 */
public class PublishNewPostMethod {
    private static PublishNewPostMethod instance;

    private Retrofit retrofit;

    private IPublishNewPostService service;

    private PublishNewPostMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

        service = retrofit.create(IPublishNewPostService.class);
    }

    public static PublishNewPostMethod getInstance() {
        if (instance == null) {
            synchronized (PublishNewPostMethod.class) {
                if (instance == null) {
                    instance = new PublishNewPostMethod();
                }
            }
        }
        return instance;
    }

    public void publishNewPost(Observer<PublishSuccess> observer, NewPost newPost) {
        service.postNewPost(newPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
