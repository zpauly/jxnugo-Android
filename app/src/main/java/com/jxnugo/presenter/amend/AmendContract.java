package com.jxnugo.presenter.amend;

import org.json.JSONObject;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;
import com.jxnugo.entity.user.UserBasicInfo;
import com.jxnugo.entity.user.amend.AmendUserInfo;

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
