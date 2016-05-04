package market.zy.com.myapplication.activity.usermanager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.ui.material.MaterialDrawerActivity;
import market.zy.com.myapplication.adapter.fragmentAdapter.UserManagerFragmentAdapter;
import market.zy.com.myapplication.R;

public class UserManagerActivity extends MaterialDrawerActivity {
    @Bind(R.id.user_manager_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.user_manager_viewpager)
    protected ViewPager mViewPager;

    @Bind(R.id.user_manager_tablayout)
    protected TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        setOnBackTwiceToTrue();

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        setupWindowEnterFadeAnimations();
        setupWindowExitFadeAnimations();

        setUpToolbar();

        setUpViewPager();
    }

    private void setUpToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.user_manage);
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void setUpViewPager() {
        if (mViewPager != null) {
            UserManagerFragmentAdapter adapter = new UserManagerFragmentAdapter(getSupportFragmentManager());
            adapter.addFragments(new LoginFragment(), getString(R.string.login));
            adapter.addFragments(new RegisteFragment(), getString(R.string.registe));
            adapter.addFragments(new UsersLoginFragment(), getString(R.string.users_login));
            mViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }
}
