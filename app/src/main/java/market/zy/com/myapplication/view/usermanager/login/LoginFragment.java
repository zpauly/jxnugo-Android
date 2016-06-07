package market.zy.com.myapplication.view.usermanager.login;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.base.BaseFragment;
import market.zy.com.myapplication.entity.user.login.LoginTokenSuccess;
import market.zy.com.myapplication.utils.SPUtil;

/**
 * Created by zpauly on 2016/3/8.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;

    @Bind(R.id.login_username)
    protected EditText mEditText;

    @Bind(R.id.login_password)
    protected PasswordEditText mPasswordEditText;

    @Bind(R.id.confirm_to_login)
    protected Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStop() {
        mPresenter.stop();
        super.onPause();
    }

    private void initView() {
        new LoginPresenter(this, getContext());
        mPresenter.start();

        mButton.setEnabled(false);

        setTextListener();

        setLoginButton();
    }

    private void setTextListener() {
        TextWatcher watcher = new Watcher();
        mEditText.addTextChangedListener(watcher);
        mPasswordEditText.addTextChangedListener(watcher);
    }

    @Override
    public void inputError() {
        showSnackbarTipShort(getView(), R.string.username_or_password_error);
    }

    @Override
    public void completeLogin() {
        getActivity().finish();
    }

    @Override
    public void insertCurrentUserIntoSP(LoginTokenSuccess loginTokenSuccess) {
        SPUtil sp = SPUtil.getInstance(getContext());
        sp.putCurrentUsername(mEditText.getText().toString());
        sp.putCurrentPassword(mPasswordEditText.getText().toString());
        sp.putCurrentUserId(loginTokenSuccess.getUserId());
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
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
                mPresenter.login(mEditText, mPasswordEditText);
            }
        });
    }
}
