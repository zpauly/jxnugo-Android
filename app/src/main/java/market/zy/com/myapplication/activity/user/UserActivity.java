package market.zy.com.myapplication.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.usermanager.UserManagerActivity;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PersonalAdapter;

/**
 * Created by dell on 2016/3/11.
 */
public class UserActivity extends BaseActivity {
    @Bind(R.id.personal_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.personal_bg_image)
    protected ImageView mImageView;

    @Bind(R.id.personal_avater)
    protected CircleImageView mAvater;

    @Bind(R.id.username_personal)
    protected TextView username;

    @Bind(R.id.user_manager_personal)
    protected TextView userManage;

    @Bind(R.id.personal_recyclerview)
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        setOnBackTwiceToTrue();

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        setUpToolbar();
        setUpRecyclerView();
        setText();
    }

    private void setText() {
        userManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UserActivity.this, UserManagerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.user_info);
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

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));
        mRecyclerView.setAdapter(new PersonalAdapter(UserActivity.this));
    }
}
