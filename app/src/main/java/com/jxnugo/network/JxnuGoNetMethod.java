package com.jxnugo.network;

import com.jxnugo.Constants;
import com.jxnugo.entity.post.OnePagePost;
import com.jxnugo.entity.post.collection.CollectPost;
import com.jxnugo.entity.post.collection.CollectStates;
import com.jxnugo.entity.post.collection.CollectionPosts;
import com.jxnugo.entity.post.collection.JudgeCollectPost;
import com.jxnugo.entity.post.collection.JudgeCollectStates;
import com.jxnugo.entity.post.collection.UncollectPost;
import com.jxnugo.entity.post.collection.UncollectStates;
import com.jxnugo.entity.post.comments.AllComments;
import com.jxnugo.entity.post.comments.NewComment;
import com.jxnugo.entity.post.comments.NewCommentStates;
import com.jxnugo.entity.post.delete.DeletePost;
import com.jxnugo.entity.post.delete.DeleteStates;
import com.jxnugo.entity.post.publish.NewPost;
import com.jxnugo.entity.post.publish.PublishStates;
import com.jxnugo.entity.post.search.SearchKeyWords;
import com.jxnugo.entity.post.user.UserPosts;
import com.jxnugo.entity.user.UserBasicInfo;
import com.jxnugo.entity.user.amend.AmendStates;
import com.jxnugo.entity.user.amend.AmendUserInfo;
import com.jxnugo.entity.user.follow.Follow;
import com.jxnugo.entity.user.follow.FollowStates;
import com.jxnugo.entity.user.follow.JudgeFollow;
import com.jxnugo.entity.user.follow.JudgeFollowStates;
import com.jxnugo.entity.user.follow.UnFollow;
import com.jxnugo.entity.user.follow.UnfollowStates;
import com.jxnugo.entity.user.follow.UserFollowed;
import com.jxnugo.entity.user.follow.UserFollowers;
import com.jxnugo.entity.user.login.LoginTokenSuccess;
import com.jxnugo.entity.user.registe.RegisteStates;
import com.jxnugo.entity.user.registe.RegisterInfo;
import com.jxnugo.utils.RetrofitUtil;
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
        if (instance == null) {
            synchronized (JxnuGoNetMethod.class) {
                if (instance == null) {
                    instance = new JxnuGoNetMethod();
                }
            }
        }
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

    public void registe(Observer<RegisteStates> observer, RegisterInfo info) {
        service.registe(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void amendUserInfo(Observer<AmendStates> observer, String auth, AmendUserInfo amendUseInfo) {
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

    public void publishNewPost(Observer<PublishStates> observer, String auth, NewPost newPost) {
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

    public void addNewComments(Observer<NewCommentStates> observer, String auth, NewComment newComment) {
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



    public void getUserFollowed(Observer<UserFollowed> observer, String auth, int userId) {
        service.getUserFollowed(auth, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getUserFollowers(Observer<UserFollowers> observer, String auth, int userId) {
        service.getUserFollowers(auth, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void collectOnePost(Observer<CollectStates> observer, String auth, CollectPost collectPost) {
        service.collectOnePost(auth, collectPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void uncollectPost(Observer<UncollectStates> observer, String auth, UncollectPost uncollectPost) {
        service.uncollectPost(auth, uncollectPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void judgeCollectPost(Observer<JudgeCollectStates> observer, String auth, JudgeCollectPost judgeCollectPost) {
        service.judgeCollectPost(auth, judgeCollectPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void followUser(Observer<FollowStates> observer, String auth, Follow follow) {
        service.followUser(auth, follow)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void unfollowUser(Observer<UnfollowStates> observer, String auth, UnFollow follow) {
        service.unfollowUser(auth, follow)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void judgeFollowUser(Observer<JudgeFollowStates> observer, String auth, JudgeFollow follow) {
        service.judgeFollowUser(auth, follow)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void deletePost(Observer<DeleteStates> observer, String auth, DeletePost post) {
        service.deletePost(auth, post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getPostsByTag(Observer<OnePagePost> observer, int tagId, int pageId) {
        service.getPostsByTag(tagId, pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void searchPosts(Observer<OnePagePost> observer, String auth, SearchKeyWords keyWords) {
        service.searchPosts(auth, keyWords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
