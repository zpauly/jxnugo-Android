package market.zy.com.myapplication.network.comments;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.comments.AllComments;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-20.
 */
public class CommentsMethod {
    private CommentsMethod instance;

    private Retrofit retrofit;

    private ICommentsService service;

    private CommentsMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

        service = retrofit.create(ICommentsService.class);
    }

    private CommentsMethod getInstance() {
        if (instance == null) {
            synchronized (CommentsMethod.class) {
                if (instance == null) {
                    instance = new CommentsMethod();
                }
            }
        }
        return instance;
    }

    public void getAllComments(Observer<AllComments> observer, String auth, int postId) {
        service.getAllComments(auth, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
