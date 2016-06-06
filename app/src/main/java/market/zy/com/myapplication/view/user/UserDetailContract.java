package market.zy.com.myapplication.view.user;

import android.view.View;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;

/**
 * Created by zpauly on 16-6-4.
 */
public class UserDetailContract {
    interface Presenter extends BasePresenter {
        void follow();

        void unfollow();

        void isAuthorFollowed();

        boolean isFollowed();
    }

    interface View extends BaseView<Presenter> {
        void showFollowSuccess(android.view.View view, int stringRes);

        void showUnFollowSuccess(android.view.View view, int stringRes);
    }
}
