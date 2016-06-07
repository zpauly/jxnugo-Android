package market.zy.com.myapplication.view.usermanager.login;

import android.content.Context;
import android.widget.EditText;

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
    private String auth;

    public LoginPresenter(LoginContract.View loginView, Context context) {
        mContext = context;
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }

    @Override
    public void login(EditText usernameEt, EditText passwordEt) {
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        loginSubscriber = new Subscriber<LoginTokenSuccess>() {
            @Override
            public void onCompleted() {
                loadUserInfo();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(LoginTokenSuccess loginTokenSuccess) {
                mLoginView.insertCurrentUserIntoSP(loginTokenSuccess);
            }
        };
        String auth = AuthUtil.getAuthFromUsernameAndPassword(username, password);
        netMethod.login(loginSubscriber, auth, username, password);
    }

    @Override
    public void loadUserInfo() {
        userinfoSubscriber = new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {
                mLoginView.completeLogin();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mLoginView.inputError();
            }

            @Override
            public void onNext(UserBasicInfo info) {
                UserInfoDao.insertUserInfo(info);
            }
        };
        netMethod.getUserInfo(userinfoSubscriber, auth, SPUtil.getInstance(mContext).getCurrentUserId());
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
    }

    @Override
    public void stop() {
        if (loginSubscriber != null)
            if (loginSubscriber.isUnsubscribed()) {
                loginSubscriber.unsubscribe();
            }
        if (userinfoSubscriber != null)
            if (userinfoSubscriber.isUnsubscribed()) {
                userinfoSubscriber.unsubscribe();
            }
    }
}
