package market.zy.com.myapplication.activity.usermanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseFragment;

/**
 * Created by dell on 2016/3/9.
 */
public class UsersLoginFragment extends BaseFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_userslogin, container, false);
        return view;
    }
}
