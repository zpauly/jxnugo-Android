package market.zy.com.myapplication.view.usermanager.login;

import android.widget.EditText;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.user.login.LoginTokenSuccess;

/**
 * Created by zpauly on 16-6-2.
 */
public interface LoginContract {
    interface Presenter extends BasePresenter {
        void login(EditText usernameEt, EditText passwordEt);

        void loadUserInfo();
    }

    interface View extends BaseView<Presenter> {
        void inputError();

        void completeLogin();

        void insertCurrentUserIntoSP(LoginTokenSuccess loginTokenSuccess);
    }
}
