package market.zy.com.myapplication.presenter.amend;

import org.json.JSONObject;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.entity.user.amend.AmendUserInfo;

/**
 * Created by zpauly on 16-6-3.
 */
public interface AmendContract {
    public interface Presenter extends BasePresenter {
        void setUserInfo();

        void amend();

        void uploadAvatar(String avatar);
    }

    public interface View extends BaseView<Presenter> {
        AmendUserInfo getText();

        UserBasicInfo getNewUserInfo();

        void showUploadError();

        void showUploadSuccess();

        void getAvatarKey(JSONObject response);
    }
}