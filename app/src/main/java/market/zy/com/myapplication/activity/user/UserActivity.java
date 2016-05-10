package market.zy.com.myapplication.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.activity.PleaseLoginFragment;

/**
 * Created by dell on 2016/3/11.
 */
public class UserActivity extends BaseActivity {
    @Bind(R.id.personal_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.personal_image_header)
    protected ImageView mHeaderImageView;

    @Bind(R.id.personal_avater)
    protected CircleImageView mAvatarImageView;

    private PleaseLoginFragment mLoginPage;

    private UserBottomSheet mBottomSheet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        selectPage();
    }

    private void initView() {
        setUpToolbar();

        selectPage();
    }

    private void selectPage() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (isCurrentUsing()) {

        } else {
            mLoginPage = new PleaseLoginFragment();
            ft.replace(R.id.personal_page, mLoginPage);
        }
        ft.commit();
    }

    private void setUpToolbar() {
        if (isCurrentUsing()) {
            mToolbar.setTitle(getCurrentUser());
        } else {
            mToolbar.setTitle(R.string.user_info);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personal_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.personal_person :
                mBottomSheet = new UserBottomSheet();
                mBottomSheet.show(getSupportFragmentManager(), mBottomSheet.getTag());
                break;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
