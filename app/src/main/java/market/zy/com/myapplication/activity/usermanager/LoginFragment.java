package market.zy.com.myapplication.activity.usermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.maksim88.passwordedittext.PasswordEditText;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.widget.LoginButton;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseFragment;
import market.zy.com.myapplication.db.UserInfoDao;
import market.zy.com.myapplication.entity.login.LoginTokenSuccess;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.network.login_register.LoginMethod;
import market.zy.com.myapplication.network.user.UserInfoMethod;
import market.zy.com.myapplication.utils.AccessTokenKeeper;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import market.zy.com.myapplication.utils.qqUtils.AppConstants;
import market.zy.com.myapplication.utils.qqUtils.LoginUiListener;
import rx.Subscriber;

/**
 * Created by zpauly on 2016/3/8.
 */
public class LoginFragment extends BaseFragment {
    private static final int QQ_BUTTON = 1;
    private static final int SINA_BUTTON = 2;

    @Bind(R.id.login_username)
    protected EditText mEditText;

    @Bind(R.id.login_password)
    protected PasswordEditText mPasswordEditText;

    @Bind(R.id.confirm_to_login)
    protected Button mButton;

    @Bind(R.id.qq_login_button)
    protected ImageButton mQQLoginButton;

    @Bind(R.id.sina_login_button)
    protected LoginButton mSinaLoginButton;


    private static Tencent mTencent;
    private IUiListener listener;

    private AuthInfo mAuthInfo;

    private int clickedButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        mButton.setEnabled(false);

        setTextListener();

        setLoginButton();

        setQQLogin();

        setSinaLogin();
    }

    private void setTextListener() {
        TextWatcher watcher = new Watcher();
        mEditText.addTextChangedListener(watcher);
        mPasswordEditText.addTextChangedListener(watcher);
    }

    private class Watcher implements TextWatcher {
        void setButtonEnabled() {
            if (mEditText.getText() == null || mEditText.getText().toString().equals("")
                    || mPasswordEditText.getText() == null || mPasswordEditText.getText().toString().equals(""))
                mButton.setEnabled(false);
            else
                mButton.setEnabled(true);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            setButtonEnabled();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setButtonEnabled();
        }

        @Override
        public void afterTextChanged(Editable s) {
            setButtonEnabled();
        }
    }

    private void setLoginButton() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Subscriber<LoginTokenSuccess> subscriber = new Subscriber<LoginTokenSuccess>() {
                    @Override
                    public void onCompleted() {
                        loadUserInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(getView(), R.string.username_or_password_error, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginTokenSuccess loginTokenSuccess) {
                        SPUtil sp = SPUtil.getInstance(getContext());
                        sp.putCurrentUsername(mEditText.getText().toString());
                        sp.putCurrentPassword(mPasswordEditText.getText().toString());
                        sp.putCurrentUserId(loginTokenSuccess.getUserId());
                    }
                };
                String username = mEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String auth = AuthUtil.getAuthFromUsernameAndPassword(username, password);
                LoginMethod.getInstance()
                        .login(subscriber, auth, username, password);
            }
        });
    }

    private void loadUserInfo() {
        Subscriber<UserBasicInfo> subscriber = new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {
                getActivity().finish();
            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(getView(), R.string.username_or_password_error, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(UserBasicInfo info) {
                UserInfoDao.insertUserInfo(info);
            }
        };
        SPUtil sp = SPUtil.getInstance(getContext());
        UserInfoMethod.getInstance(sp.getCurrentUsername(), sp.getCurrentPassword())
                .getUserInfo(subscriber, sp.getCurrentUserId());
    }

    private void setQQLogin() {
        listener = new LoginUiListener();
        if (mTencent == null) {
            mTencent = Tencent.createInstance(AppConstants.APP_ID, getContext().getApplicationContext());
        }

        mQQLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedButton = QQ_BUTTON;
                if (mTencent != null) {
                    if (!mTencent.isSessionValid())
                    {
                        mTencent.login(LoginFragment.this, AppConstants.SCPOE_ALL, listener);
                    }
                }
            }
        });
    }

    private void setSinaLogin() {
        mAuthInfo = new AuthInfo(getActivity()
                , Constants.APP_KEY
                , Constants.REDIRECT_URL
                , Constants.SCOPE);

        mSinaLoginButton.setWeiboAuthInfo(mAuthInfo, new WeiboAuthListener() {
            @Override
            public void onComplete(Bundle bundle) {
                clickedButton = SINA_BUTTON;
                Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
                if (mAccessToken.isSessionValid()) {
                    AccessTokenKeeper.writeAccessToken(getContext().getApplicationContext(), mAccessToken);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                clickedButton = SINA_BUTTON;
            }

            @Override
            public void onCancel() {
                clickedButton = SINA_BUTTON;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (clickedButton) {
            case QQ_BUTTON :
                if (mQQLoginButton != null) {
                    mTencent.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case SINA_BUTTON :
                if (mSinaLoginButton != null) {
                    mSinaLoginButton.onActivityResult(requestCode, resultCode, data);
                }
                break;
            default:
                break;
        }
    }
}
