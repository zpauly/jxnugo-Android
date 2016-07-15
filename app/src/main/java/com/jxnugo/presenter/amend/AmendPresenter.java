package com.jxnugo.presenter.amend;

import android.content.Context;

import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

import com.jxnugo.db.user.UserInfoDao;
import com.jxnugo.db.user.UserInfoModel;
import com.jxnugo.utils.qiniu.UploadImages;
import com.jxnugo.entity.qiniu.QiniuUploadToken;
import com.jxnugo.entity.user.UserBasicInfo;
import com.jxnugo.entity.user.amend.AmendStates;
import com.jxnugo.entity.user.amend.AmendUserInfo;
import com.jxnugo.network.JxnuGoNetMethod;
import com.jxnugo.network.qiniu.upload.OnUploadListener;
import com.jxnugo.network.qiniu.uploadtoken.TokenMethod;
import com.jxnugo.utils.AuthUtil;
import com.jxnugo.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-3.
 */
public class AmendPresenter implements AmendContract.Presenter {
    private AmendContract.View mAmendView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;
    private TokenMethod tokenMethod;
    private String auth;
    private UserInfoModel userInfo;

    private Subscriber<AmendStates> amendSubscriber;
    private Subscriber<QiniuUploadToken> tokenSubscriber;

    private String avatarKey;
    private String token;

    public AmendPresenter(AmendContract.View view, Context context) {
        mAmendView = view;
        mContext = context;
        mAmendView.setPresenter(this);
    }

    @Override
    public void setUserInfo() {
        UserBasicInfo info = mAmendView.getNewUserInfo();
        UserInfoDao.deleteUserInfo();
        UserInfoDao.insertUserInfo(info);
    }

    @Override
    public void amend() {
        amendSubscriber = new Subscriber<AmendStates>() {
            @Override
            public void onCompleted() {
                //upload successly
                mAmendView.showUploadSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mAmendView.showUploadError();
            }

            @Override
            public void onNext(AmendStates amendSuccess) {

            }
        };
        AmendUserInfo amendUserInfo = mAmendView.getText();
        netMethod.amendUserInfo(amendSubscriber, auth, amendUserInfo);
    }

    @Override
    public void uploadAvatar(final String avatar) {
        tokenSubscriber = new Subscriber<QiniuUploadToken>() {
            @Override
            public void onCompleted() {
                UploadImages.getInstance().uploadImages(avatar, token
                        , new OnUploadListener() {
                            @Override
                            public void onCompleted(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    mAmendView.getAvatarKey(response);
                                } else {
                                }
                            }

                            @Override
                            public void onProcessing(String key, double percent) {

                            }

                            @Override
                            public boolean onCancelled() {
                                return false;
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(QiniuUploadToken qiniuUploadToken) {
                token = qiniuUploadToken.getUptoken();
            }
        };
        tokenMethod.getUploadToken(tokenSubscriber);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
        tokenMethod = TokenMethod.getInstance();
        userInfo = UserInfoDao.queryUserInfo();
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
    }

    @Override
    public void stop() {
        if (amendSubscriber != null) {
            if (amendSubscriber.isUnsubscribed()) {
                amendSubscriber.unsubscribe();
            }
        }
        if (tokenSubscriber != null) {
            if (tokenSubscriber.isUnsubscribed()) {
                tokenSubscriber.unsubscribe();
            }
        }
    }
}
