package market.zy.com.myapplication.view.user;

import android.view.View;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.user.follow.JudgeFollowStates;

/**
 * Created by zpauly on 16-6-4.
 */
public class UserDetailContract {
    interface Presenter extends BasePresenter {
        void follow();

        void unfollow();

        void isAuthorFollowed();
    }

    interface View extends BaseView<Presenter> {
        void setFollowState(JudgeFollowStates judgeFollowStates);

        void unfollowSuccess();

        void followSuccess();
    }
}
