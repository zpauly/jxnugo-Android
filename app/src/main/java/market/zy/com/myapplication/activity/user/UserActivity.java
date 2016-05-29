package market.zy.com.myapplication.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.utils.SPUtil;

/**
 * Created by zpauly on 2016/3/11.
 */
public class UserActivity extends BaseActivity {
    public static final int SELF = 0;
    public static final int OTHERS = 1;

    public static final String PERSON = "PERSON";

    @Bind(R.id.personal_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.personal_image_header)
    protected ImageView mHeaderImageView;

    @Bind(R.id.personal_avater)
    protected CircleImageView mAvatarImageView;

    private Fragment mLoginPage;

    private UserBottomSheet mBottomSheet;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private int person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mBottomSheet != null)
        mBottomSheet.dismiss();
        super.onSaveInstanceState(outState);
    }

    private void initView() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        selectPage();

        setUpToolbar();
    }

    private void selectPage() {
        if (isCurrentUsing()) {
            mLoginPage = new UserDetailFragment();
            Glide.with(this)
                    .load(userInfo.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .centerCrop()
                    .into(mAvatarImageView);
            ft.replace(R.id.personal_page, mLoginPage);
            ft.commit();
        } else {
            mLoginPage = new PleaseLoginFragment();
            ft.replace(R.id.personal_page, mLoginPage);
            ft.commit();
        }
    }

    private void setUpToolbar() {
        if (isCurrentUsing()) {
            mToolbar.setTitle(userInfo.getName());
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
        if (person == -1)
            return false;
        if (person == 0) {
            getMenuInflater().inflate(R.menu.personal_toolbar_menu, menu);
        }
        if (person == 1) {

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (person == -1)
            return super.onOptionsItemSelected(item);
        if (person == 0) {
            switch (item.getItemId()) {
                case R.id.personal_person:
                    if (SPUtil.getInstance(this).getCurrentUsername() != null) {
                        mBottomSheet = new UserBottomSheet();
                        mBottomSheet.show(getSupportFragmentManager(), mBottomSheet.getTag());
                    } else {
                        showSnackbarTipShort(mToolbar, R.string.please_login);
                    }
                    break;
                default:
                    break;
            }
        }
        if (person == 1) {
            switch (item.getItemId()) {

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
