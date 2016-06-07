package market.zy.com.myapplication.view.comments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nineoldandroids.view.ViewPropertyAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.base.BaseActivity;
import market.zy.com.myapplication.presenter.comments.CommentsContract;
import market.zy.com.myapplication.presenter.comments.CommentsPresenter;
import market.zy.com.myapplication.view.trade.TradeActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.CommentsAdapter;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.post.comments.NewComment;
import market.zy.com.myapplication.entity.post.comments.NewCommentStates;
import market.zy.com.myapplication.ui.support.DividerItemDecoration;
import rx.Subscriber;

/**
 * Created by zpauly on 16-5-10.
 */
public class CommentsActivity extends BaseActivity implements CommentsContract.View {
    private CommentsContract.Presenter mPresenter;

    @Bind(R.id.comments_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.comments_recyclerview)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.add_comment_layout)
    protected RelativeLayout mAddCommentLayout;

    @Bind(R.id.comments_add_comment)
    protected EditText mAddCommentEditText;

    @Bind(R.id.comments_send_comment)
    protected ImageView mSendCommentButton;

    private CommentsAdapter mRAdapter;

    private int postId;

    private MaterialDialog uploadDialog;
    private MaterialDialog confirmDialog;

    private Subscriber<AllComments> subscriber;
    private Subscriber<NewCommentStates> newCommentSuccessSubscriber;

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
    protected void onPause() {
        mPresenter.stop();
        super.onPause();
    }

    private void initView() {
        new CommentsPresenter(this, this);
        mPresenter.start();
        setUpToolbar();
        setUpRecyclerView();
        setUpButton();
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

    private void setUpButton() {
        mSendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddCommentEditText.getText() == null || mAddCommentEditText.getText().toString().equals("")) {
                    showSnackbarTipShort(getCurrentFocus(), R.string.no_comment);
                    return;
                }
                confirmDialog = new MaterialDialog.Builder(CommentsActivity.this)
                        .title(R.string.upload)
                        .content(R.string.can_upload)
                        .positiveText(R.string.yes)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                putNewComment();
                                uploadDialog = new MaterialDialog.Builder(CommentsActivity.this)
                                        .title(R.string.uploading)
                                        .content(R.string.please_wait)
                                        .progress(true, 0)
                                        .show();
                            }
                        })
                        .negativeText(R.string.no)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void putNewComment() {
        NewComment newComment = new NewComment();
        newComment.setBody(mAddCommentEditText.getText().toString());
        newComment.setPostId(postId);
        newComment.setUserId(userInfo.getUserId());
        mPresenter.putNewComment(newComment);
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
                if (dy > 0) {
                    ViewPropertyAnimator.animate(mAddCommentLayout).translationY(mAddCommentLayout.getHeight());
                } else if (dy < 0) {
                    ViewPropertyAnimator.animate(mAddCommentLayout).translationY(0);
                }
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
        mPresenter.loadCommentData(postId);
    }

    private void getPostId() {
        postId = getIntent().getIntExtra(TradeActivity.POST_ID, -1);
    }

    @Override
    public void setPresenter(CommentsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void putNewCommentSuccess() {
        uploadDialog.dismiss();
        showSnackbarTipShort(getCurrentFocus(), R.string.upload_successly);
        initView();
    }

    @Override
    public void putNewCommentFail() {
        uploadDialog.dismiss();
        showSnackbarTipShort(getCurrentFocus(), R.string.error_upload);
    }

    @Override
    public void addComments(AllComments allComments) {
        mRAdapter.clearData();
        mRAdapter.addAllData(allComments.getComments());
    }
}
