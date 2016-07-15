package com.jxnugo.presenter.user.userinfo;

import android.content.Context;

import com.jxnugo.db.user.UserInfoDao;
import com.jxnugo.db.user.UserInfoModel;
import com.jxnugo.entity.post.collection.CollectionPosts;
import com.jxnugo.entity.post.user.UserPosts;
import com.jxnugo.entity.user.follow.UserFollowed;
import com.jxnugo.entity.user.follow.UserFollowers;
import com.jxnugo.network.JxnuGoNetMethod;
import com.jxnugo.utils.AuthUtil;
import com.jxnugo.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-6.
 */
public class UserInfoPresenter implements UserInfoContract.Presenter {
    private UserInfoContract.View mUserinfoView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;
    private UserInfoModel userInfo;
    private String auth;

    private int userId;

    private Subscriber<CollectionPosts> collectionPostsSubscriber;
    private Subscriber<UserPosts> userPostsSubscriber;
    private Subscriber<UserFollowed> followedSubscirber;
    private Subscriber<UserFollowers> followersSubscriber;

    public UserInfoPresenter(UserInfoContract.View view, Context context, int userId) {
        mUserinfoView = view;
        mContext = context;
        this.userId = userId;
        mUserinfoView.setPresenter(this);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
        userInfo = UserInfoDao.queryUserInfo();
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
    }

    @Override
    public void stop() {
        if (collectionPostsSubscriber != null) {
            if (collectionPostsSubscriber.isUnsubscribed()) {
                collectionPostsSubscriber.unsubscribe();
            }
        }
        if (userPostsSubscriber != null) {
            if (userPostsSubscriber.isUnsubscribed()) {
                userPostsSubscriber.unsubscribe();
            }
        }
        if (followedSubscirber != null) {
            if (followedSubscirber.isUnsubscribed()) {
                followedSubscirber.unsubscribe();
            }
        }
        if (followersSubscriber != null) {
            if (followersSubscriber.isUnsubscribed()) {
                followersSubscriber.unsubscribe();
            }
        }
    }

    @Override
    public void loadPostsData() {
        userPostsSubscriber = new Subscriber<UserPosts>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(UserPosts userPosts) {
                mUserinfoView.addAllPostsData(userPosts);
            }
        };
        JxnuGoNetMethod.getInstance().getUserPosts(userPostsSubscriber, auth, userId);
    }

    @Override
    public void loadCollectionData() {
        collectionPostsSubscriber = new Subscriber<CollectionPosts>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(CollectionPosts collectionPosts) {
                mUserinfoView.addAllCollectionData(collectionPosts);
            }
        };
        JxnuGoNetMethod.getInstance().getCollectionPosts(collectionPostsSubscriber, auth, userId);
    }

    @Override
    public void loadFollowersData() {
        followersSubscriber = new Subscriber<UserFollowers>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserFollowers userFollowers) {
                mUserinfoView.addAllFollowersData(userFollowers);
            }
        };
        JxnuGoNetMethod.getInstance().getUserFollowers(followersSubscriber, auth, userId);
    }

    @Override
    public void loadFollowedData() {
        followedSubscirber = new Subscriber<UserFollowed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserFollowed userFollowed) {
                mUserinfoView.addAllFollowedData(userFollowed);
            }
        };
        JxnuGoNetMethod.getInstance().getUserFollowed(followedSubscirber, auth, userId);
    }
}
