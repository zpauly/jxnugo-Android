package market.zy.com.myapplication.view.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
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
import market.zy.com.myapplication.base.BaseActivity;
import market.zy.com.myapplication.view.comments.CommentsActivity;
import market.zy.com.myapplication.view.trade.TradeActivity;
import market.zy.com.myapplication.view.user.UserActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostDetailCommentsAdapter;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostDetailPhotosAdapter;
import market.zy.com.myapplication.db.post.PhotoModel;
import market.zy.com.myapplication.db.post.PhotosDao;
import market.zy.com.myapplication.db.post.PostDetailModel;
import market.zy.com.myapplication.db.post.PostDetailDao;
import market.zy.com.myapplication.db.user.OtherInfoDao;
import market.zy.com.myapplication.entity.post.collection.CollectPost;
import market.zy.com.myapplication.entity.post.collection.CollectStates;
import market.zy.com.myapplication.entity.post.collection.JudgeCollectPost;
import market.zy.com.myapplication.entity.post.collection.JudgeCollectStates;
import market.zy.com.myapplication.entity.post.collection.UncollectPost;
import market.zy.com.myapplication.entity.post.collection.UncollectStates;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
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

    @Bind(R.id.post_good_location)
    protected TextView mPostGoodLocation;

    @Bind(R.id.post_good_contact)
    protected TextView mPostGoodContact;

    @Bind(R.id.post_good_details)
    protected TextView mPostGoodDetails;

    @Bind(R.id.post_good_photos_recyclerview)
    protected RecyclerView mPostGoodPhotosRecyclerView;

    @Bind(R.id.post_comments_recyclerview)
    protected RecyclerView mPostCommentsRecyclerview;

    @Bind(R.id.post_comments_all)
    protected TextView mPostCommentsAll;

    @Bind(R.id.post_detail_add_shopping)
    protected ImageView mPostDetailAddShopping;

    @Bind(R.id.post_detail_share)
    protected ImageView mPostDetailShare;

    @Bind(R.id.post_detail_follow)
    protected ImageView mPostDetailFollow;

    @Bind(R.id.post_seller_avatar)
    protected CircleImageView mPostSellerAvatar;

    @Bind(R.id.post_seller_name)
    protected TextView mPostSellerName;

    @Bind(R.id.post_maincontent)
    protected CoordinatorLayout mPostMaincontent;

    @Bind(R.id.post_comments_layout)
    protected LinearLayout mCommentsLayout;

    @Bind(R.id.post_detail_layout)
    protected NestedScrollView mNestedScrollView;

    private PostDetailCommentsAdapter mCommentsAdapter;
    private PostDetailPhotosAdapter mPhotosAdapter;

    private int postId;
    private String auth;
    private PostDetailModel postDetail;

    private boolean isCollected = false;
    private boolean isFollowed = false;

    private Subscriber<AllComments> subscriber;
    private Observable<List<PhotoModel>> observable;
    private Subscriber<JudgeCollectStates> judgeCollectSubscriber;
    private Subscriber<CollectStates> collectSubscriber;
    private Subscriber<UncollectStates> uncollectSubsriber;
    private Subscriber<UserBasicInfo> publisheraSubscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(this).getCurrentUsername()
                , SPUtil.getInstance(this).getCurrentPassword());

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCommentData();
        isPostCollected();
    }

    @Override
    protected void onPause() {
        unsubscribe();
        super.onPause();
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
        if (judgeCollectSubscriber != null) {
            judgeCollectSubscriber.unsubscribe();
        }
        if (collectSubscriber != null) {
            collectSubscriber.unsubscribe();
        }
        if (uncollectSubsriber != null) {
            uncollectSubsriber.unsubscribe();
        }
    }

    private void initViews() {
        getPostId();

        setUpToolbar();

        setUpCommentsRecyclerView();

        loadPostData();

        setUpCommentsLayout();

        setUpPhotosRecyclerView();

        setUpImageButtons();

        isPostCollected();

        loadPublisherInfo();
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

    private void setUpImageButtons() {
        mPostDetailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                /*String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);*/
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        mPostDetailAddShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollected) {
                    uncollect();
                } else {
                    collect();
                }
            }
        });
        mPostDetailFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str[] = postDetail.getAuthor().split("/");
                int followId = Integer.parseInt(str[str.length - 1]);
                Intent intent = new Intent();
                intent.setClass(PostDetailsActivity.this, UserActivity.class);
                intent.putExtra(UserActivity.PERSON, UserActivity.OTHERS);
                intent.putExtra(UserActivity.OTHER_ID, followId);
                startActivity(intent);
            }
        });
    }

    private void setUpPhotosRecyclerView() {
        mPhotosAdapter = new PostDetailPhotosAdapter(this);
        loadPhotosData();
        mPostGoodPhotosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPostGoodPhotosRecyclerView.setAdapter(mPhotosAdapter);
        mPostGoodPhotosRecyclerView.getLayoutManager();

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
        JxnuGoNetMethod.getInstance().getAllComments(subscriber, auth, postId);
    }

    private void loadPhotosData() {
        observable = Observable.create(new Observable.OnSubscribe<List<PhotoModel>>() {

            @Override
            public void call(Subscriber<? super List<PhotoModel>> subscriber) {
                subscriber.onNext(PhotosDao.queryPhoto(postId));
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PhotoModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        
                    }

                    @Override
                    public void onNext(List<PhotoModel> photoKeys) {
                        mPhotosAdapter.swapData(photoKeys);
                    }
                });
    }

    private void loadPostData() {
        postDetail = PostDetailDao.queryPostDetail(postId).get(0);
        mPostGoodName.setText(postDetail.getGoodsName());
        mPostGoodBuyTime.setText(postDetail.getGoodsBuyTime());
        mPostSellerName.setText(postDetail.getPostNickName());
        mPostGoodPrice.setText(mPostGoodPrice.getText().toString() + postDetail.getGoodsPrice());
        mPostGoodDetails.setText(postDetail.getBody());
        mPostGoodQuality.setText(postDetail.getGoodsQuality());
        mPostGoodLocation.setText(postDetail.getGoodsLocation());
        mPostGoodContact.setText(postDetail.getContact());
        Glide.with(this)
                .load(postDetail.getPostUserAvatar())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mPostSellerAvatar);
        String key = getIntent().getStringExtra(TradeActivity.POST_COVER);
        Glide.with(this)
                .load(Constants.PIC_BASE_URL
                        + key)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mPostHeaderImage);
    }

    private void isPostCollected() {
        judgeCollectSubscriber = new Subscriber<JudgeCollectStates>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JudgeCollectStates judgeCollectSuccess) {
                if (judgeCollectSuccess.getCollectInfo() == 0) {
                    mPostDetailAddShopping.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    isCollected = false;
                } else if (judgeCollectSuccess.getCollectInfo() == 1) {
                    mPostDetailAddShopping.setImageResource(R.drawable.ic_favorite_black_24dp);
                    isCollected = true;
                }
            }
        };
        JudgeCollectPost post = new JudgeCollectPost();
        post.setPostId(postId);
        post.setUserId(userInfo.getUserId());
        JxnuGoNetMethod.getInstance().judgeCollectPost(judgeCollectSubscriber, auth, post);
    }

    private void collect() {
        collectSubscriber = new Subscriber<CollectStates>() {
            @Override
            public void onCompleted() {
                showSnackbarTipShort(getCurrentFocus(), R.string.collected_true);
                isPostCollected();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(CollectStates collectSuccess) {

            }
        };
        CollectPost post = new CollectPost();
        post.setPostId(postId);
        post.setUserId(userInfo.getUserId());
        JxnuGoNetMethod.getInstance().collectOnePost(collectSubscriber, auth, post);
    }

    private void uncollect() {
        uncollectSubsriber = new Subscriber<UncollectStates>() {
            @Override
            public void onCompleted() {
                showSnackbarTipShort(getCurrentFocus(), R.string.collected_false);
                isPostCollected();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(UncollectStates uncollectSuccess) {

            }
        };
        UncollectPost post = new UncollectPost();
        post.setUserId(userInfo.getUserId());
        post.setPostId(postId);
        JxnuGoNetMethod.getInstance().uncollectPost(uncollectSubsriber, auth, post);
    }

    private void loadPublisherInfo() {
        publisheraSubscriber = new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(UserBasicInfo userBasicInfo) {
                OtherInfoDao.insertOtherInfo(userBasicInfo);
            }
        };
        String str[] = postDetail.getAuthor().split("/");
        int followId = Integer.parseInt(str[str.length - 1]);
        JxnuGoNetMethod.getInstance().getUserInfo(publisheraSubscriber, auth, followId);
    }
}
