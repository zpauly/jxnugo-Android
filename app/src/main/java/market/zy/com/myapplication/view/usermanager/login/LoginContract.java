package market.zy.com.myapplication.view.usermanager.login;

import android.widget.EditText;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;

/**
 * Created by zpauly on 16-6-2.
 */
public interface LoginContract {
    interface Presenter extends BasePresenter {
        void login(EditText usernameEt, EditText passwordEt);

        void loadUserInfo(android.view.View view);
    }

    interface View extends BaseView<Presenter> {
        void inputError(android.view.View view, int stringRes);

        void completeLogin();
    }
}
