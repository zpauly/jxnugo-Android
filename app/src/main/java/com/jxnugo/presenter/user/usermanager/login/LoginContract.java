package com.jxnugo.presenter.user.usermanager.login;

import android.widget.EditText;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;
import com.jxnugo.entity.user.login.LoginTokenSuccess;

/**
 * Created by zpauly on 16-6-2.
 */
public class LoginContract {
    public interface Presenter extends BasePresenter {
        void login(EditText usernameEt, EditText passwordEt);

        void loadUserInfo();
    }

    public interface View extends BaseView<Presenter> {
        void inputError();

        void completeLogin();

        void insertCurrentUserIntoSP(LoginTokenSuccess loginTokenSuccess);
    }
}
