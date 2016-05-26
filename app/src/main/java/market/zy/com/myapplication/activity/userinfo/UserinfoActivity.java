package market.zy.com.myapplication.activity.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostCollectionAdapter;

/**
 * Created by zpauly on 16-5-25.
 */
public class UserinfoActivity extends BaseActivity {
    public static final String USERINFO_PAGE = "USERINFO_PAGE";

    public static final int MY_POST = 1;
    public static final int MY_COLLECTION = 2;
    public static final int MY_FOLLOWING = 3;
    public static final int MY_FOLLOWERS = 4;
    public static final String SELECT_PAGE = "SELECT_PAGE";

    @Bind(R.id.userinfo_appbar)
    protected AppBarLayout mAppbarLayout;

    @Bind(R.id.userinfo_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.userinfo_recyclerview)
    protected RecyclerView mRecyclerView;

    private int pageId;

    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        pageId = getIntent().getIntExtra(USERINFO_PAGE, -1);

        initViews();
    }

    private void initViews() {
        setUpToolbar();

        setUpRecyclerView();
    }


    private void setUpToolbar() {
        if (pageId != -1) {
            switch (pageId) {
                case MY_POST:
                    mToolbar.setTitle(R.string.my_post);
                    break;
                case MY_COLLECTION:
                    mToolbar.setTitle(R.string.my_collection);
                    break;
                case MY_FOLLOWING:
                    mToolbar.setTitle(R.string.my_following);
                    break;
                case MY_FOLLOWERS:
                    mToolbar.setTitle(R.string.my_followers);
                    break;
            }
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

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (pageId == -1)
            return;
        switch (pageId) {
            case MY_POST :
                mAdapter = new PostCollectionAdapter(this);
                break;
            case MY_COLLECTION :
                mAdapter = new PostCollectionAdapter(this);
                break;
            case MY_FOLLOWING :
                break;
            case MY_FOLLOWERS :
                break;
        }
    }

    private void loadPostsData() {

    }

    private void loadCollectionData() {

    }
}
