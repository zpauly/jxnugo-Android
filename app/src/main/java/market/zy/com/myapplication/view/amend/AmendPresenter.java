package market.zy.com.myapplication.view.amend;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.qiniu.android.http.ResponseInfo;

import org.json.JSONException;
import org.json.JSONObject;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.db.user.UserInfoDao;
import market.zy.com.myapplication.db.user.UserInfoModel;
import market.zy.com.myapplication.utils.qiniu.UploadImages;
import market.zy.com.myapplication.entity.qiniu.QiniuUploadToken;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.entity.user.amend.AmendStates;
import market.zy.com.myapplication.entity.user.amend.AmendUserInfo;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.network.qiniu.upload.OnUploadListener;
import market.zy.com.myapplication.network.qiniu.uploadtoken.TokenMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-3.
 */
public class AmendPresenter implements AmendContract.Presenter {
    private AmendContract.View mAmendView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;
    private TokenMethod tokenMethod;

    private Subscriber<AmendStates> amendSubscriber;
    private Subscriber<QiniuUploadToken> tokenSubscriber;

    private MaterialDialog confirmDialog;
    private MaterialDialog uploadDialog;

    private String avatarKey;
    private String token;

    private EditText mNickNameEt, mTag, mContact, mLocation, mSex;
    private AppCompatSpinner mSexSeleter;

    public AmendPresenter(AmendContract.View view, Context context) {
        mAmendView = view;
        mContext = context;
    }

    @Override
    public void setUserInfo() {
        UserInfoModel userInfo = UserInfoDao.queryUserInfo();
        UserBasicInfo info = new UserBasicInfo();
        info.setUserId(userInfo.getUserId());
        info.setFollowed(userInfo.getFollowed());
        info.setFollowers(userInfo.getFollowers());
        info.setLast_seen(userInfo.getLast_seen());
        info.setMember_since(userInfo.getMember_since());
        info.setPostCollectionCount(userInfo.getPostCollectionCount());
        info.setPostCount(userInfo.getPostCount());
        info.setUserName(userInfo.getUserName());
        info.setName(mNickNameEt.getText().toString());
        info.setAvatar(avatarKey);
        info.setAbout_me(mTag.getText().toString());
        info.setContactMe(mContact.getText().toString());
        info.setLocation(mLocation.getText().toString());
        info.setSex(mSex.getPrivateImeOptions());
        UserInfoDao.deleteUserInfo();
        UserInfoDao.insertUserInfo(info);
    }

    @Override
    public void amend() {
        amendSubscriber = new Subscriber<AmendStates>() {
            @Override
            public void onCompleted() {
                //upload successly
                mAmendView.showUploadSuccess(mNickNameEt, R.string.upload_successly);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mAmendView.showUploadError(mNickNameEt, R.string.error_upload);
            }

            @Override
            public void onNext(AmendStates amendSuccess) {

            }
        };
        AmendUserInfo amendUserInfo = mAmendView.getText(avatarKey);
        String auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
        JxnuGoNetMethod.getInstance().amendUserInfo(amendSubscriber, auth, amendUserInfo);
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
                                    try {
                                        String photoKey = response.getString("key");
                                        avatarKey = Constants.PIC_BASE_URL + photoKey;
                                        amend();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
    }

    @Override
    public void stop() {
        if (amendSubscriber != null || !amendSubscriber.isUnsubscribed()) {
            amendSubscriber.unsubscribe();
        }
        if (tokenSubscriber != null || !tokenSubscriber.isUnsubscribed()) {
            tokenSubscriber.unsubscribe();
        }
    }
}
