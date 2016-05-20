package market.zy.com.myapplication.network.post;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.db.UserInfo;
import market.zy.com.myapplication.db.UserInfoDao;
import market.zy.com.myapplication.entity.post.OnePagePost;
import market.zy.com.myapplication.utils.RetrofitUtil;
import market.zy.com.myapplication.utils.SPUtil;
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

    private UserInfo userInfo;

    private PagePostsMethod(String username, String password) {
        userInfo = UserInfoDao.queryUserInfo();

        retrofit = RetrofitUtil.initAuthRetrofit(Constants.BASE_URL, username, password);

        service = retrofit.create(IPagePostsService.class);
    }

    public static PagePostsMethod getInstance(String username, String password) {
        instance = new PagePostsMethod(username, password);

        return instance;
    }

    public void getOnePagePosts(Observer<OnePagePost> observer, int pageId) {
        service.getOnePagePost(pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
