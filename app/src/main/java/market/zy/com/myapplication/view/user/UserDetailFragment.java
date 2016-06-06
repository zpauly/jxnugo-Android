package market.zy.com.myapplication.view.user;


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
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.base.BaseFragment;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PersonalAdapter;
import market.zy.com.myapplication.entity.user.follow.Follow;
import market.zy.com.myapplication.entity.user.follow.FollowStates;
import market.zy.com.myapplication.entity.user.follow.JudgeFollow;
import market.zy.com.myapplication.entity.user.follow.JudgeFollowStates;
import market.zy.com.myapplication.entity.user.follow.UnFollow;
import market.zy.com.myapplication.entity.user.follow.UnfollowStates;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.ui.support.DividerItemDecoration;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.personal_details_fragment, container, false);
        ButterKnife.bind(this, mView);

        if (((UserActivity) getActivity()).getPerson() == UserActivity.OTHERS) {
            mFollowLayout.setVisibility(View.VISIBLE);
            otherId = ((UserActivity) getActivity()).getOtherId();
            new UserDetailPresenter(this, getContext(), mFollowImage, mFollowText, otherId);
            mPresenter.start();
            isAuthorFollowed();
            mFollowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter.isFollowed()) {
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
    public void showFollowSuccess(View view, int stringRes) {
        showSnackbarTipShort(view, stringRes);
    }

    @Override
    public void showUnFollowSuccess(View view, int stringRes) {
        showSnackbarTipShort(view, stringRes);
    }
}
