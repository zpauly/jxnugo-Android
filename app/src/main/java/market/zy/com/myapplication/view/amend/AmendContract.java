package market.zy.com.myapplication.view.amend;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.user.amend.AmendUserInfo;

/**
 * Created by zpauly on 16-6-3.
 */
public interface AmendContract {
    interface Presenter extends BasePresenter {
        void setUserInfo();

        void amend();

        void uploadAvatar(String avatar);
    }

    interface View extends BaseView<Presenter> {
        AmendUserInfo getText(String avatar);

        void showUploadError(android.view.View view, int stringRes);

        void showUploadSuccess(android.view.View view, int stringRes);
    }
}
