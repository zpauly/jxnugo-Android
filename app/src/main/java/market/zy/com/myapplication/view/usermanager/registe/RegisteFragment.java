package market.zy.com.myapplication.view.usermanager.registe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.maksim88.passwordedittext.PasswordEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.base.BaseFragment;
import market.zy.com.myapplication.entity.user.registe.RegisteStates;
import market.zy.com.myapplication.entity.user.registe.RegisterInfo;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import rx.Subscriber;

/**
 * Created by zpauly on 2016/3/8.
 */
public class RegisteFragment extends BaseFragment {
    @Bind(R.id.registe_username)
    protected EditText mRegisteUsername;

    @Bind(R.id.registe_email)
    protected EditText mRegisteEmail;

    @Bind(R.id.registe_password)
    protected PasswordEditText mRegistePassword;

    @Bind(R.id.registe_password_again)
    protected PasswordEditText mRegistePasswordAgain;

    @Bind(R.id.confirm)
    protected Button registeButton;

    private Subscriber<RegisteStates> registeSubscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onPause() {
        unsubscribe();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    private void unsubscribe() {
        if (registeSubscriber != null || !registeSubscriber.isUnsubscribed()) {
            registeSubscriber.unsubscribe();
        }
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        registeButton.setEnabled(false);

        setTextListener();

        setRegisteButton();
    }

    private void setTextListener() {
        TextWatcher watcher = new Watcher();
        mRegisteUsername.addTextChangedListener(watcher);
        mRegisteEmail.addTextChangedListener(watcher);
        mRegistePassword.addTextChangedListener(watcher);
        mRegistePasswordAgain.addTextChangedListener(watcher);
    }

    private void setRegisteButton() {
        registeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRegistePassword.getText().toString().equals(mRegistePasswordAgain.getText().toString())) {
                    showSnackbarTipShort(v, R.string.passowrd_different);
                    return;
                }
                if (!verifyEmail(mRegisteEmail)) {
                    showSnackbarTipShort(v, R.string.wrong_email);
                    return;
                }
                registe();
            }
        });
    }

    private void registe() {
        RegisterInfo info = new RegisterInfo();
        info.setUserName(mRegisteUsername.getText().toString());
        info.setUserEmail(mRegisteEmail.getText().toString());
        info.setPassWord(mRegistePassword.getText().toString());

        registeSubscriber = new Subscriber<RegisteStates>() {
            @Override
            public void onCompleted() {
                showSnackbarTipShort(mRegisteUsername, R.string.registe_success);
            }

            @Override
            public void onError(Throwable e) {
                showSnackbarTipShort(mRegisteUsername, R.string.error_upload);
                e.printStackTrace();
            }

            @Override
            public void onNext(RegisteStates registeSuccess) {

            }
        };
        JxnuGoNetMethod.getInstance().registe(registeSubscriber, info);
    }

    private boolean verifyEmail(EditText emailEt) {
        String email = emailEt.getText().toString();

        String regex = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            emailEt.setText("");
            return false;
        }
    }

    private class Watcher implements TextWatcher {
        void setButtonEnabled() {
            if (mRegisteUsername.getText() == null || "".equals(mRegisteUsername.getText().toString())
                    ||mRegisteEmail.getText() == null || "".equals(mRegisteEmail.getText().toString())
                    || mRegistePassword.getText() == null || "".equals(mRegistePassword.getText().toString())
                    || mRegistePasswordAgain.getText() == null || "".equals(mRegistePasswordAgain.getText().toString()))
                registeButton.setEnabled(false);
            else
                registeButton.setEnabled(true);
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
}
