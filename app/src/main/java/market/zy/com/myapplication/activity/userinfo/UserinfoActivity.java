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
import market.zy.com.myapplication.adapter.recyclerviewAdapter.FollowerFollowingAdapter;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostCollectionAdapter;
import market.zy.com.myapplication.db.post.PostDetailDao;
import market.zy.com.myapplication.db.post.PostDetailModel;
import market.zy.com.myapplication.entity.post.OneSimplePost;
import market.zy.com.myapplication.entity.post.collection.CollectionPosts;
import market.zy.com.myapplication.entity.post.user.UserPosts;
import market.zy.com.myapplication.entity.user.follow.UserFollowed;
import market.zy.com.myapplication.entity.user.follow.UserFollowers;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-5-25.
 */
public class UserinfoActivity extends BaseActivity {
    public static final String USERINFO_PAGE = "USERINFO_PAGE";
    public static final String USER_ID = "USER_ID";

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
    private int userId;

    private RecyclerView.Adapter mAdapter;

    private String auth;

    private Subscriber<CollectionPosts> collectionPostsSubscriber;
    private Subscriber<UserPosts> userPostsSubscriber;
    private Subscriber<UserFollowed> followedSubscirber;
    private Subscriber<UserFollowers> followersSubscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        pageId = getIntent().getIntExtra(USERINFO_PAGE, -1);
        userId = getIntent().getIntExtra(USER_ID, userInfo.getUserId());

        initViews();
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
        if (collectionPostsSubscriber != null) {
            collectionPostsSubscriber.unsubscribe();
        }
        if (userPostsSubscriber != null) {
            userPostsSubscriber.unsubscribe();
        }
        if (followedSubscirber != null) {
            followedSubscirber.unsubscribe();
        }
        if (followersSubscriber != null) {
            followersSubscriber.unsubscribe();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpRecyclerView();
    }

    private void initViews() {
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(this).getCurrentUsername()
                ,SPUtil.getInstance(this).getCurrentPassword());

        setUpToolbar();

        setUpRecyclerView();
    }


    private void setUpToolbar() {
        if (pageId != -1) {
            switch (pageId) {
                case MY_POST:
                    if (userId == userInfo.getUserId())
                        mToolbar.setTitle(R.string.my_post);
                    else
                        mToolbar.setTitle(R.string.other_post);
                    break;
                case MY_COLLECTION:
                    if (userId == userInfo.getUserId())
                        mToolbar.setTitle(R.string.my_collection);
                    else
                        mToolbar.setTitle(R.string.other_collection);
                    break;
                case MY_FOLLOWING:
                    if (userId == userInfo.getUserId())
                        mToolbar.setTitle(R.string.my_following);
                    else
                        mToolbar.setTitle(R.string.other_following);
                    break;
                case MY_FOLLOWERS:
                    if (userId == userInfo.getUserId())
                        mToolbar.setTitle(R.string.my_followers);
                    else
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
                mRecyclerView.setAdapter(mAdapter);
                loadPostsData((PostCollectionAdapter) mAdapter);
                break;
            case MY_COLLECTION :
                mAdapter = new PostCollectionAdapter(this);
                mRecyclerView.setAdapter(mAdapter);
                loadCollectionData((PostCollectionAdapter) mAdapter);
                break;
            case MY_FOLLOWING :
                mAdapter = new FollowerFollowingAdapter(this);
                mRecyclerView.setAdapter(mAdapter);
                loadFollowedData((FollowerFollowingAdapter) mAdapter);
                break;
            case MY_FOLLOWERS :
                mAdapter = new FollowerFollowingAdapter(this);
                mRecyclerView.setAdapter(mAdapter);
                loadFollowersData((FollowerFollowingAdapter) mAdapter);
                break;
        }
    }

    private void loadPostsData(final PostCollectionAdapter adapter) {
        userPostsSubscriber = new Subscriber<UserPosts>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(UserPosts userPosts) {
                adapter.addAllData(userPosts.getUserPosts());
                for (OneSimplePost post : userPosts.getUserPosts()) {
                    PostDetailDao.insertPostDetail(post);
                }
            }
        };
        JxnuGoNetMethod.getInstance().getUserPosts(userPostsSubscriber, auth, userId);
    }

    private void loadCollectionData(final PostCollectionAdapter adapter) {
        collectionPostsSubscriber = new Subscriber<CollectionPosts>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(CollectionPosts collectionPosts) {
                adapter.addAllData(collectionPosts.getCollectionPost());
                for (OneSimplePost post : collectionPosts.getCollectionPost()) {
                    PostDetailDao.insertPostDetail(post);
                }
            }
        };
        JxnuGoNetMethod.getInstance().getCollectionPosts(collectionPostsSubscriber, auth, userId);
    }

    private void loadFollowersData(final FollowerFollowingAdapter adapter) {
        followersSubscriber = new Subscriber<UserFollowers>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserFollowers userFollowers) {
                adapter.addAllData(userFollowers.getFollowers());
            }
        };
        JxnuGoNetMethod.getInstance().getUserFollowers(followersSubscriber, auth, userId);
    }

    private void loadFollowedData(final FollowerFollowingAdapter adapter) {
        followedSubscirber = new Subscriber<UserFollowed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserFollowed userFollowed) {
                adapter.addAllData(userFollowed.getFollowed());
            }
        };
        JxnuGoNetMethod.getInstance().getUserFollowed(followedSubscirber, auth, userId);
    }
}
