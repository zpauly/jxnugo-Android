package market.zy.com.myapplication.presenter.user;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.user.follow.JudgeFollowStates;

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
