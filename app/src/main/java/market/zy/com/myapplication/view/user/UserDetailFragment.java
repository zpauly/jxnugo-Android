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
public class UserDetailFragment extends BaseFragment {

    private View mView;

    @Bind(R.id.personal_detail_follow_layout)
    protected LinearLayout mFollowLayout;

    @Bind(R.id.personal_detail_follow_imageview)
    protected ImageView mFollowImage;

    @Bind(R.id.personal_detail_follow_text)
    protected TextView mFollowText;

    @Bind(R.id.personal_detail_recyclerview)
    protected RecyclerView mRecyclerView;

    private boolean isFollowed = false;
    private String auth;
    private int otherId;

    private Subscriber<FollowStates> followSubscriber;
    private Subscriber<UnfollowStates> unfollowSubscriber;
    private Subscriber<JudgeFollowStates> judgeFollowSubscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.personal_details_fragment, container, false);
        ButterKnife.bind(this, mView);

        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(getContext()).getCurrentUsername()
                , SPUtil.getInstance(getContext()).getCurrentPassword());

        if (((UserActivity) getActivity()).getPerson() == UserActivity.OTHERS) {
            mFollowLayout.setVisibility(View.VISIBLE);
            otherId = ((UserActivity) getActivity()).getOtherId();
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
        unsubscribe();
        super.onPause();
    }

    private void unsubscribe() {
        if (followSubscriber != null) {
            followSubscriber.unsubscribe();
        }
        if (unfollowSubscriber != null) {
            unfollowSubscriber.unsubscribe();
        }
        if (judgeFollowSubscriber != null) {
            judgeFollowSubscriber.unsubscribe();
        }
    }

    private void follow() {
        followSubscriber = new Subscriber<FollowStates>() {
            @Override
            public void onCompleted() {
                isAuthorFollowed();
                showSnackbarTipShort(getView(), R.string.follow_true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FollowStates followStates) {

            }
        };
        Follow follow = new Follow();
        follow.setUserId(userInfo.getUserId());
        follow.setFollowedId(otherId);
        JxnuGoNetMethod.getInstance().followUser(followSubscriber, auth, follow);
    }

    private void unfollow() {
        unfollowSubscriber = new Subscriber<UnfollowStates>() {
            @Override
            public void onCompleted() {
                isAuthorFollowed();
                showSnackbarTipShort(getView(), R.string.follow_false);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UnfollowStates unfollowStates) {

            }
        };
        UnFollow follow = new UnFollow();
        follow.setUserId(userInfo.getUserId());
        follow.setUnfollowedId(otherId);
        JxnuGoNetMethod.getInstance().unfollowUser(unfollowSubscriber, auth, follow);
    }

    private void isAuthorFollowed() {
        judgeFollowSubscriber = new Subscriber<JudgeFollowStates>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(JudgeFollowStates judgeFollowStates) {
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
        };
        JudgeFollow follow = new JudgeFollow();
        follow.setUserId(userInfo.getUserId());
        follow.setFollowerId(otherId);
        JxnuGoNetMethod.getInstance().judgeFollowUser(judgeFollowSubscriber, auth, follow);
    }
}
