package market.zy.com.myapplication.network;

import android.database.Observable;

import market.zy.com.myapplication.Constants;
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
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-22.
 */
public class JxnuGoNetMethod {
    private static JxnuGoNetMethod instance;

    private Retrofit retrofit;

    private IJxnuGoNetService service;

    private JxnuGoNetMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

        service = retrofit.create(IJxnuGoNetService.class);
    }

    public static JxnuGoNetMethod getInstance() {
        instance = new JxnuGoNetMethod();

        return instance;
    }

    public JxnuGoNetMethod resetUrl(String url) {
        retrofit = RetrofitUtil.initRetrofit(url);

        service = retrofit.create(IJxnuGoNetService.class);

        return this;
    }

    public void getUserInfo(Observer<UserBasicInfo> observer, String auth, int userId) {
        service.getUserInfo(auth, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void login(Observer<LoginTokenSuccess> observer, String auth, String username, String password) {
        service.getLoginToken(auth, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void registe(Observer<RegisteSuccess> observer, RegisterInfo info) {
        service.registe(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void amendUserInfo(Observer<AmendSuccess> observer, String auth, AmendUseInfo amendUseInfo) {
        service.amendUsetInfo(auth, amendUseInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getOnePagePosts(Observer<OnePagePost> observer, int pageId) {
        service.getOnePagePost(pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void publishNewPost(Observer<PublishSuccess> observer, String auth, NewPost newPost) {
        service.postNewPost(auth, newPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getAllComments(Observer<AllComments> observer, String auth, int postId) {
        service.getAllComments(auth, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void addNewComments(Observer<NewCommentSuccess> observer, String auth, NewComment newComment) {
        service.addNewComment(auth, newComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCollectionPosts(Observer<CollectionPosts> observer, String auth, int userId) {
        service.getCollectionPosts(auth, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getUserPosts(Observer<UserPosts> observer, String auth, int userId) {
        service.getUserPosts(auth, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
