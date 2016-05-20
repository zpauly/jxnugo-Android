package market.zy.com.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-20.
 */
public class PostDetailsActivity extends BaseActivity {
    @Bind(R.id.post_header_image)
    protected ImageView mPostHeaderImage;

    @Bind(R.id.post_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.post_collapse_toolbar)
    protected CollapsingToolbarLayout mCollapseToolbar;

    @Bind(R.id.post_appbar)
    protected AppBarLayout mAppbarLayout;

    @Bind(R.id.post_good_name)
    protected TextView mPostGoodName;

    @Bind(R.id.post_good_buy_time)
    protected TextView mPostGoodBuyTime;

    @Bind(R.id.post_good_quality)
    protected TextView mPostGoodQuality;

    @Bind(R.id.post_good_price)
    protected TextView mPostGoodPrice;

    @Bind(R.id.post_good_details)
    protected TextView mPostGoodDetails;

    @Bind(R.id.post_good_photos)
    protected RecyclerView mPostGoodPhotos;

    @Bind(R.id.post_comments_recyclerview)
    protected RecyclerView mPostCommentsRecyclerview;

    @Bind(R.id.post_comments_all)
    protected TextView mPostCommentsAll;

    @Bind(R.id.post_seller_avatar)
    protected CircleImageView mPostSellerAvatar;

    @Bind(R.id.pos_seller_name)
    protected TextView mPosSellerName;

    @Bind(R.id.post_maincontent)
    protected CoordinatorLayout mPostMaincontent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        initViews();
    }

    private void initViews() {
        setUpToolbar();
    }

    private void setUpToolbar() {
        mToolbar.setTitle("");
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
