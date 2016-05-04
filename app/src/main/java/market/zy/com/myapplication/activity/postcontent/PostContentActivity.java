package market.zy.com.myapplication.activity.postcontent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.ui.material.MaterialBottomSheetActivity;
import market.zy.com.myapplication.utils.sinaUtils.WeiboShare;

/**
 * Created by zpauly on 16-3-24.
 */
public class PostContentActivity extends MaterialBottomSheetActivity {
    @Bind(R.id.content_toolbar)
    protected Toolbar mContentToolbar;

    @Bind(R.id.content_card_toolbar)
    protected Toolbar mCardToolbar;

    @Bind(R.id.content_comment)
    protected ImageView mCommentImageView;

    @Bind(R.id.content_collect)
    protected ImageView mCollectImageView;

    @Bind(R.id.content_connect)
    protected ImageView mConnectImageView;

    @Bind(R.id.content_page_layout)
    protected LinearLayout mContentLayout;

    private boolean isCollected;
    private boolean isTitleShowing;

    private IWeiboShareAPI weiboShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        setOnBackTwiceToTrue();

        isCollected = false;
        isTitleShowing = false;

        initView();

        setWeiboShare();

        if (savedInstanceState != null) {
            weiboShareAPI.handleWeiboResponse(getIntent(), new IWeiboHandler.Response() {
                @Override
                public void onResponse(BaseResponse baseResponse) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_share :
                showShareBottomSheet(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 :
                                Snackbar.make(getCurrentFocus(), "weibo", Snackbar.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        ButterKnife.bind(this);

        setUpToolbar();

        setUpSubToolBar();

        addImageViewDynamiclly(mContentLayout, R.mipmap.cheese_1);

        for (int i = 0; i < 50; i++) {
            addTextViewDynamiclly(mContentLayout, "introduction");
            if (i == 17) {
                addImageViewDynamiclly(mContentLayout, R.mipmap.cheese_3);
            }
        }

    }

    private void setUpToolbar() {
        if (mContentToolbar != null) {
            mContentToolbar.setTitle(R.string.content);
            mContentToolbar.setBackgroundResource(R.mipmap.header);
            setSupportActionBar(mContentToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            mContentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        if (mCardToolbar != null) {
            mCardToolbar.setLogo(R.mipmap.ic_launcher);
            mCardToolbar.setTitle("ZhangYu");
            mCardToolbar.setSubtitle("this is my post content");
        }
    }

    private void setUpSubToolBar() {
        mCommentImageView.setImageResource(R.drawable.ic_mode_comment_24dp);
        mCollectImageView.setImageResource(R.drawable.ic_favorite_outline_24dp);
        mConnectImageView.setImageResource(R.drawable.ic_local_phone_24dp);
        mCollectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollected) {
                    mCollectImageView.setImageResource(R.drawable.ic_favorite_outline_24dp);
                    showSnackbarTipShort(mCollectImageView, R.string.collected_false);
                    isCollected = false;
                } else {
                    mCollectImageView.setImageResource(R.drawable.ic_favorite_24dp);
                    showSnackbarTipShort(mCollectImageView, R.string.collected_true);
                    isCollected = true;
                }
            }
        });
    }

    private void addImageViewDynamiclly(ViewGroup mViewGroup, URL url) {
        ImageView newImageView = new ImageView(PostContentActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 8, 0, 8);
        newImageView.setLayoutParams(layoutParams);
        Glide.with(this)
                .load(url)
                .fitCenter()
                .into(newImageView);
        mViewGroup.addView(newImageView, -1, layoutParams);
    }

    private void addImageViewDynamiclly(ViewGroup mViewGroup, int resId) {
        ImageView newImageView = new ImageView(PostContentActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 8, 0, 8);
        Glide.with(this)
                .load(resId)
                .fitCenter()
                .into(newImageView);
        mViewGroup.addView(newImageView, -1, layoutParams);
    }

    private void addTextViewDynamiclly(ViewGroup mViewGroup, String str) {
        TextView newTextView = new TextView(PostContentActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 8, 0, 8);
        newTextView.setTextSize(15f);
        newTextView.setText(str);
        mViewGroup.addView(newTextView, -1, layoutParams);
    }

    private void setWeiboShare() {
        weiboShareAPI = WeiboShare.getWeiboAPIInstance();
    }
}