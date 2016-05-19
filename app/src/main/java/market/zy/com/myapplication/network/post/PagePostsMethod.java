package market.zy.com.myapplication.network.post;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.post.OnePagePost;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-19.
 */
public class PagePostsMethod {
    private static PagePostsMethod instance;

    private Retrofit retrofit;

    private IPagePostsService service;

    private PagePostsMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

        service = retrofit.create(IPagePostsService.class);
    }

    public static PagePostsMethod getInstance() {
        if (instance == null) {
            synchronized (PagePostsMethod.class) {
                if (instance == null) {
                    instance = new PagePostsMethod();
                }
            }
        }
        return instance;
    }

    public void getOnePagePosts(Observer<OnePagePost> observer, int pageId) {
        service.getOnePagePost(pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
