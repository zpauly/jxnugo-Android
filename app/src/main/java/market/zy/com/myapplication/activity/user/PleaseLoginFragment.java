package market.zy.com.myapplication.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.usermanager.UserManagerActivity;

/**
 * Created by zpauly on 16-5-10.
 */
public class PleaseLoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.please_login_fragment, container, false);
        Button mButton = (Button) mView.findViewById(R.id.login_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserManagerActivity.class);
                startActivity(intent);
            }
        });
        return mView;
    }
}
