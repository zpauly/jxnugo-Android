package com.jxnugo.presenter.user.usermanager.registe;

import android.content.Context;
import android.widget.EditText;

import com.jxnugo.R;
import com.jxnugo.entity.user.registe.RegisteStates;
import com.jxnugo.entity.user.registe.RegisterInfo;
import com.jxnugo.network.JxnuGoNetMethod;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-2.
 */
public class RegistePresenter implements RegisteContract.Presenter {
    private RegisteContract.View mRegisteView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;

    private Subscriber<RegisteStates> registeSubscriber;

    public RegistePresenter(RegisteContract.View view, Context context) {
        mRegisteView = view;
        mContext = context;
        mRegisteView.setPresenter(this);
    }

    @Override
    public void registe(final EditText usernameEt, EditText passwordEt, EditText emailEt) {
        RegisterInfo info = new RegisterInfo();
        info.setUserName(usernameEt.getText().toString());
        info.setUserEmail(emailEt.getText().toString());
        info.setPassWord(passwordEt.getText().toString());

        registeSubscriber = new Subscriber<RegisteStates>() {
            @Override
            public void onCompleted() {
                mRegisteView.showRegisteSuccess(usernameEt, R.string.registe_success);
            }

            @Override
            public void onError(Throwable e) {
                mRegisteView.showUploadError(usernameEt, R.string.error_upload);
                e.printStackTrace();
            }

            @Override
            public void onNext(RegisteStates registeSuccess) {

            }
        };
        netMethod.registe(registeSubscriber, info);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
    }

    @Override
    public void stop() {
        if (registeSubscriber != null) {
            if (registeSubscriber.isUnsubscribed()) {
                registeSubscriber.unsubscribe();
            }
        }
    }
}
