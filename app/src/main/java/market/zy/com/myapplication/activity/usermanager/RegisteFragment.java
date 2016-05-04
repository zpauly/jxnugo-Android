package market.zy.com.myapplication.activity.usermanager;

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
import market.zy.com.myapplication.activity.BaseFragment;

/**
 * Created by dell on 2016/3/8.
 */
public class RegisteFragment extends BaseFragment {
    @Bind(R.id.registe_email)
    protected EditText registeEmail;

    @Bind(R.id.registe_password)
    protected PasswordEditText registePassword;

    @Bind(R.id.registe_password_again)
    protected PasswordEditText registePasswordAgain;

    @Bind(R.id.confirm)
    protected Button registeButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        registeButton.setEnabled(false);

        setTextListener();

        setRegisteButton();
    }

    private void setTextListener() {
        TextWatcher watcher = new Watcher();
        registeEmail.addTextChangedListener(watcher);
        registePassword.addTextChangedListener(watcher);
        registePasswordAgain.addTextChangedListener(watcher);
    }

    private void setRegisteButton() {
        registeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!registePassword.getText().toString().equals(registePasswordAgain.getText().toString())) {
                    showSnackbarTipShort(v, R.string.passowrd_different);
                }
            }
        });
    }

    private class Watcher implements TextWatcher {
        void setButtonEnabled() {
            if (registeEmail.getText() == null || "".equals(registeEmail.getText().toString())
                    || registePassword.getText() == null || "".equals(registePassword.getText().toString())
                    || registePasswordAgain.getText() == null || "".equals(registePasswordAgain.getText().toString()))
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
