package com.jxnugo.view.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.jxnugo.R;
import com.jxnugo.adapter.recyclerviewAdapter.PersonalAdapter;
import com.jxnugo.base.BaseFragment;
import com.jxnugo.entity.user.follow.JudgeFollowStates;
import com.jxnugo.presenter.user.UserDetailContract;
import com.jxnugo.presenter.user.UserDetailPresenter;
import com.jxnugo.ui.support.DividerItemDecoration;

/**
 * Created by zpauly on 16-5-18.
 */
public class UserDetailFragment extends BaseFragment implements UserDetailContract.View {
    private UserDetailContract.Presenter mPresenter;

    private View mView;

    @Bind(R.id.personal_detail_follow_layout)
    protected LinearLayout mFollowLayout;

    @Bind(R.id.personal_detail_follow_imageview)
    protected ImageView mFollowImage;

    @Bind(R.id.personal_detail_follow_text)
    protected TextView mFollowText;

    @Bind(R.id.personal_detail_recyclerview)
    protected RecyclerView mRecyclerView;

    private int otherId;
    private boolean isFollowed = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.personal_details_fragment, container, false);
        ButterKnife.bind(this, mView);

        if (((UserActivity) getActivity()).getPerson() == UserActivity.OTHERS) {
            mFollowLayout.setVisibility(View.VISIBLE);
            otherId = ((UserActivity) getActivity()).getOtherId();
            new UserDetailPresenter(this, getContext(), otherId);
            mPresenter.start();
            isAuthorFollowed();
            mFollowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFollowed) {
                        unfollow();
                    } else {
                        follow();
                    }
                }
            });
            mRecyclerView.setAdapter(new PersonalAdapter(getContext(), false, otherId));
        } else {
            mRecyclerView.setAdapter(new PersonalAdapter(getContext(), true, userInfo.getUserId()));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));

        return mView;
    }

    @Override
    public void onPause() {
        if (mPresenter != null) {
            mPresenter.stop();
        }
        super.onPause();
    }

    private void follow() {
        mPresenter.follow();
    }

    private void unfollow() {
        mPresenter.unfollow();
    }

    private void isAuthorFollowed() {
        mPresenter.isAuthorFollowed();
    }

    @Override
    public void setPresenter(UserDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setFollowState(JudgeFollowStates judgeFollowStates) {
        if (judgeFollowStates.getJudgeInfo() == 0) {
            mFollowImage.setImageResource(R.drawable.ic_person_outline_black_24dp);
            mFollowText.setText(R.string.click_to_follow);
            isFollowed = false;
        }
        if (judgeFollowStates.getJudgeInfo() == 1) {
            mFollowText.setText(R.string.click_to_unfollow);
            mFollowImage.setImageResource(R.drawable.ic_person_black_24dp);
            isFollowed = true;
        }
    }

    @Override
    public void unfollowSuccess() {
        isAuthorFollowed();
        showSnackbarTipShort(getView(), R.string.follow_false);
    }

    @Override
    public void followSuccess() {
        isAuthorFollowed();
        showSnackbarTipShort(getView(), R.string.follow_true);
    }
}
