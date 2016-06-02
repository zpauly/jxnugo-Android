package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.view.user.UserActivity;
import market.zy.com.myapplication.view.viewholder.FollowerFollowingViewHolder;
import market.zy.com.myapplication.db.user.OtherInfoDao;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.entity.user.follow.Follower;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-5-28.
 */
public class FollowerFollowingAdapter extends RecyclerView.Adapter<FollowerFollowingViewHolder> {
    private Context mContext;

    private List<Follower> mData = new ArrayList<>();

    public FollowerFollowingAdapter(Context context) {
        mContext = context;
    }

    public void addAllData(List<Follower> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(Follower follower) {
        mData.add(follower);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public FollowerFollowingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_follower_following, parent, false);
        FollowerFollowingViewHolder viewHolder = new FollowerFollowingViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FollowerFollowingViewHolder holder, final int position) {
        holder.mUsername.setText(mData.get(position).getUserName());
        holder.mUserTag.setText(mData.get(position).getAboutMe());
        Glide.with(mContext)
                .load(mData.get(position).getUserAvatar())
                .crossFade()
                .centerCrop()
                .into(holder.mUserAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadOthersInfo(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void loadOthersInfo(final int position) {
        Subscriber<UserBasicInfo> subscriber = new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {
                Intent intent = new Intent();
                intent.setClass(mContext, UserActivity.class);
                intent.putExtra(UserActivity.PERSON, UserActivity.OTHERS);
                intent.putExtra(UserActivity.OTHER_ID, mData.get(position).getUserId());
                mContext.startActivity(intent);
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
        String auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
        JxnuGoNetMethod.getInstance().getUserInfo(subscriber, auth, mData.get(position).getUserId());
    }
}
