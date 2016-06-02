package market.zy.com.myapplication.view.usermanager.registe;

import android.view.View;
import android.widget.EditText;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;

/**
 * Created by zpauly on 16-6-2.
 */
public interface RegisteContract {
    interface Presenter extends BasePresenter {
        void registe(EditText usernameEt, EditText passwordEt, EditText emailEt);
    }

    interface View extends BaseView<Presenter> {
        void showUploadError(android.view.View view, int stringRes);

        void showRegisteSuccess(android.view.View view, int stringRes);
    }
}
