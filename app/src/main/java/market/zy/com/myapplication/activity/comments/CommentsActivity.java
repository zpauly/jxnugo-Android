package market.zy.com.myapplication.activity.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.activity.trade.TradeActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.CommentsAdapter;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.ui.support.DividerItemDecoration;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-5-10.
 */
public class CommentsActivity extends BaseActivity {
    @Bind(R.id.comments_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.comments_recyclerview)
    protected RecyclerView mRecyclerView;

    private CommentsAdapter mRAdapter;

    private int postId;


    private Subscriber<AllComments> subscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        getPostId();

        mRAdapter = new CommentsAdapter(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    private void initView() {
        setUpToolbar();
        setUpRecyclerView();
    }

    private void unsubscribe() {
        if (subscriber != null)
            subscriber.unsubscribe();
    }

    private void setUpToolbar() {
        mToolbar.setTitle(R.string.comments);
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
        mRecyclerView.setAdapter(mRAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        final LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItemPosition =  layoutManager.findLastVisibleItemPosition();
                if (lastItemPosition == mRAdapter.getItemCount() - 2) {
                    mRAdapter.setShowLoadMore(true);
                } else if (lastItemPosition == mRAdapter.getItemCount() - 1
                        && mRAdapter.isShowingLoadMore()) {
                    mRAdapter.setShowLoadMore(true);
                } else {
                    mRAdapter.setShowLoadMore(false);
                }
            }
        });
        loadCommentData();
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
                mRAdapter.addAllData(allComments.getComments());
            }
        };
        String auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(this).getCurrentUsername()
                ,SPUtil.getInstance(this).getCurrentPassword());
        JxnuGoNetMethod.getInstance().getAllComments(subscriber, auth, postId);
    }

    private void getPostId() {
        postId = getIntent().getIntExtra(TradeActivity.POST_ID, -1);
    }
}
