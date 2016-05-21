package market.zy.com.myapplication.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.activity.comments.CommentsActivity;
import market.zy.com.myapplication.activity.trade.TradeActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostDetailCommentsAdapter;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostDetailPhotosAdapter;
import market.zy.com.myapplication.db.post.PhotoBean;
import market.zy.com.myapplication.db.post.PhotosDao;
import market.zy.com.myapplication.db.post.PostDetailBean;
import market.zy.com.myapplication.db.post.PostDetailDao;
import market.zy.com.myapplication.entity.comments.AllComments;
import market.zy.com.myapplication.network.comments.CommentsMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-20.
 */
public class PostDetailsActivity extends BaseActivity {
    public static String CURRENT_PHOTO = "CURRENT_PHOTO";

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

    @Bind(R.id.post_good_photos_recyclerview)
    protected RecyclerView mPostGoodPhotosRecyclerView;

    @Bind(R.id.post_comments_recyclerview)
    protected RecyclerView mPostCommentsRecyclerview;

    @Bind(R.id.post_comments_all)
    protected TextView mPostCommentsAll;

    @Bind(R.id.post_seller_avatar)
    protected CircleImageView mPostSellerAvatar;

    @Bind(R.id.post_seller_name)
    protected TextView mPostSellerName;

    @Bind(R.id.post_maincontent)
    protected CoordinatorLayout mPostMaincontent;

    @Bind(R.id.post_comments_layout)
    protected LinearLayout mCommentsLayout;

    private PostDetailCommentsAdapter mCommentsAdapter;
    private PostDetailPhotosAdapter mPhotosAdapter;

    private int postId;


    private Subscriber<AllComments> subscriber;
    private Observable<List<PhotoBean>> observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        initViews();
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    private void unsubscribe() {
        if (subscriber != null)
            subscriber.unsubscribe();
        if (observable != null)
            observable.unsubscribeOn(Schedulers.immediate());
    }

    private void initViews() {
        getPostId();

        setUpToolbar();

        setUpCommentsRecyclerView();

        loadPostData();

        setUpCommentsLayout();

        setUpPhotosRecyclerView();
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

    private void setUpPhotosRecyclerView() {
        mPhotosAdapter = new PostDetailPhotosAdapter(this);
        loadPhotosData();
        mPostGoodPhotosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPostGoodPhotosRecyclerView.setAdapter(mPhotosAdapter);
        /*mPostGoodPhotosRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });*/
    }

    private void setUpCommentsRecyclerView() {
        mCommentsAdapter = new PostDetailCommentsAdapter(this);
        loadCommentData();
        mPostCommentsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mPostCommentsRecyclerview.setAdapter(mCommentsAdapter);
    }

    private void setUpCommentsLayout() {
        mCommentsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent();
                commentIntent.putExtra(TradeActivity.POST_ID, postId);
                commentIntent.setClass(PostDetailsActivity.this, CommentsActivity.class);
                startActivity(commentIntent);
            }
        });
    }

    private void getPostId() {
        postId = getIntent().getIntExtra(TradeActivity.POST_ID, -1);
    }

    private void loadCommentData() {
        subscriber = new Subscriber<AllComments>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllComments allComments) {
                mCommentsAdapter.swapData(allComments.getComments());
            }
        };
        String auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(this).getCurrentUsername()
                ,SPUtil.getInstance(this).getCurrentPassword());
        CommentsMethod.getInstance().getAllComments(subscriber, auth, postId);
    }

    private void loadPhotosData() {
        observable = Observable.create(new Observable.OnSubscribe<List<PhotoBean>>() {

            @Override
            public void call(Subscriber<? super List<PhotoBean>> subscriber) {
                subscriber.onNext(PhotosDao.queryPhoto(postId));
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PhotoBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PhotoBean> photoKeys) {
                        mPhotosAdapter.swapData(photoKeys);
                    }
                });
    }

    private void loadPostData() {
        PostDetailBean postDetail = PostDetailDao.queryPostDetail(postId).get(0);
        mPostGoodName.setText(postDetail.getGoodName());
        mPostGoodBuyTime.setText(postDetail.getGoodBuyTime());
        mPostSellerName.setText(postDetail.getPostUserName());
        mPostGoodPrice.setText(mPostGoodPrice.getText().toString() + "   " +postDetail.getGoodPrice());
        mPostGoodDetails.setText(postDetail.getBody());
        mPostGoodQuality.setText(postDetail.getGoodQuality());
        String key = getIntent().getStringExtra(TradeActivity.POST_COVER);
        Glide.with(this)
                .load(Constants.PIC_BASE_URL
                        + key)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mPostHeaderImage);
    }
}
