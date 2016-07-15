package com.jxnugo.presenter.user;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;
import com.jxnugo.entity.user.follow.JudgeFollowStates;

/**
 * Created by zpauly on 16-6-4.
 */
public class UserDetailContract {
    public interface Presenter extends BasePresenter {
        void follow();

        void unfollow();

        void isAuthorFollowed();
    }

    public interface View extends BaseView<Presenter> {
        void setFollowState(JudgeFollowStates judgeFollowStates);

        void unfollowSuccess();

        void followSuccess();
    }
}
