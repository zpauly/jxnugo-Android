package com.jxnugo.presenter.user;

import android.content.Context;

import com.jxnugo.db.user.UserInfoDao;
import com.jxnugo.db.user.UserInfoModel;
import com.jxnugo.entity.user.follow.Follow;
import com.jxnugo.entity.user.follow.FollowStates;
import com.jxnugo.entity.user.follow.JudgeFollow;
import com.jxnugo.entity.user.follow.JudgeFollowStates;
import com.jxnugo.entity.user.follow.UnFollow;
import com.jxnugo.entity.user.follow.UnfollowStates;
import com.jxnugo.network.JxnuGoNetMethod;
import com.jxnugo.utils.AuthUtil;
import com.jxnugo.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-4.
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {
    private final UserDetailContract.View mUserDetailView;
    private final Context mContext;

    private JxnuGoNetMethod netMethod;
    private UserInfoModel userInfo;
    private String auth;

    private Subscriber<FollowStates> followSubscriber;
    private Subscriber<UnfollowStates> unfollowSubscriber;
    private Subscriber<JudgeFollowStates> judgeFollowSubscriber;

    private int otherId;

    public UserDetailPresenter(UserDetailContract.View view, Context context, int otherId) {
        mUserDetailView = view;
        mContext = context;
        this.otherId = otherId;
        mUserDetailView.setPresenter(this);
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
        if (followSubscriber != null) {
            if (followSubscriber.isUnsubscribed()) {
                followSubscriber.unsubscribe();
            }
        }
        if (unfollowSubscriber != null) {
            if (unfollowSubscriber.isUnsubscribed()) {
                unfollowSubscriber.unsubscribe();
            }
        }
        if (judgeFollowSubscriber != null) {
            if (judgeFollowSubscriber.isUnsubscribed()) {
                judgeFollowSubscriber.unsubscribe();
            }
        }
    }

    @Override
    public void follow() {
        followSubscriber = new Subscriber<FollowStates>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FollowStates followStates) {

            }
        };
        Follow follow = new Follow();
        follow.setUserId(userInfo.getUserId());
        follow.setFollowedId(otherId);
        netMethod.followUser(followSubscriber, auth, follow);
    }

    @Override
    public void unfollow() {
        unfollowSubscriber = new Subscriber<UnfollowStates>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UnfollowStates unfollowStates) {

            }
        };
        UnFollow follow = new UnFollow();
        follow.setUserId(userInfo.getUserId());
        follow.setUnfollowedId(otherId);
        netMethod.unfollowUser(unfollowSubscriber, auth, follow);
    }

    @Override
    public void isAuthorFollowed() {
        judgeFollowSubscriber = new Subscriber<JudgeFollowStates>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(JudgeFollowStates judgeFollowStates) {

            }
        };
        JudgeFollow follow = new JudgeFollow();
        follow.setUserId(userInfo.getUserId());
        follow.setFollowerId(otherId);
        netMethod.judgeFollowUser(judgeFollowSubscriber, auth, follow);
    }
}
