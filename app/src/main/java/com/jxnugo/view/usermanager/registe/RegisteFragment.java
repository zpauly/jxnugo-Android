package com.jxnugo.view.usermanager.registe;

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
import com.jxnugo.R;
import com.jxnugo.base.BaseFragment;
import com.jxnugo.presenter.user.usermanager.registe.RegisteContract;
import com.jxnugo.presenter.user.usermanager.registe.RegistePresenter;

/**
 * Created by zpauly on 2016/3/8.
 */
public class RegisteFragment extends BaseFragment implements RegisteContract.View {
    private RegisteContract.Presenter mPresenter;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onPause() {
        mPresenter.stop();
        super.onPause();
    }

    private void initView() {
        new RegistePresenter(this, getContext());
        mPresenter.start();

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
        mPresenter.registe(mRegisteUsername, mRegistePassword, mRegisteEmail);
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

    @Override
    public void showUploadError(View view, int stringRes) {
        showSnackbarTipShort(view, stringRes);
    }

    @Override
    public void showRegisteSuccess(View view, int stringRes) {
        showSnackbarTipShort(view, stringRes);
    }

    @Override
    public void setPresenter(RegisteContract.Presenter presenter) {
        mPresenter = presenter;
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
