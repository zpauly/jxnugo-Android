package market.zy.com.myapplication.view.usermanager.login;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.db.user.UserInfoDao;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.entity.user.login.LoginTokenSuccess;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-2.
 */
public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mLoginView;
    private final Context mContext;

    private Subscriber<LoginTokenSuccess> loginSubscriber;
    private Subscriber<UserBasicInfo> userinfoSubscriber;

    private JxnuGoNetMethod netMethod;

    public LoginPresenter(LoginContract.View loginView, Context context) {
        mContext = context;
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }

    @Override
    public void login(final EditText usernameEt, EditText passwordEt) {
        final String username = usernameEt.getText().toString();
        final String password = passwordEt.getText().toString();
        loginSubscriber = new Subscriber<LoginTokenSuccess>() {
            @Override
            public void onCompleted() {
                loadUserInfo(usernameEt);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginTokenSuccess loginTokenSuccess) {
                SPUtil sp = SPUtil.getInstance(mContext);
                sp.putCurrentUsername(username);
                sp.putCurrentPassword(password);
                sp.putCurrentUserId(loginTokenSuccess.getUserId());
            }
        };
        String auth = AuthUtil.getAuthFromUsernameAndPassword(username, password);
        netMethod.login(loginSubscriber, auth, username, password);
    }

    @Override
    public void loadUserInfo(final View view) {
        userinfoSubscriber = new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {
                mLoginView.completeLogin();
            }

            @Override
            public void onError(Throwable e) {
                mLoginView.inputError(view, R.string.username_or_password_error);
            }

            @Override
            public void onNext(UserBasicInfo info) {
                UserInfoDao.insertUserInfo(info);
            }
        };
        SPUtil sp = SPUtil.getInstance(mContext);
        String auth = AuthUtil.getAuthFromUsernameAndPassword(sp.getCurrentUsername(), sp.getCurrentPassword());
        netMethod.getUserInfo(userinfoSubscriber, auth, sp.getCurrentUserId());
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
    }

    @Override
    public void stop() {
        if (loginSubscriber != null || !loginSubscriber.isUnsubscribed())
            loginSubscriber.unsubscribe();
        if (userinfoSubscriber != null || !userinfoSubscriber.isUnsubscribed())
            userinfoSubscriber.unsubscribe();
    }
}
