package com.jxnugo.presenter.user.usermanager.registe;

import android.widget.EditText;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;

/**
 * Created by zpauly on 16-6-2.
 */
public class RegisteContract {
    public interface Presenter extends BasePresenter {
        void registe(EditText usernameEt, EditText passwordEt, EditText emailEt);
    }

    public interface View extends BaseView<Presenter> {
        void showUploadError(android.view.View view, int stringRes);

        void showRegisteSuccess(android.view.View view, int stringRes);
    }
}
