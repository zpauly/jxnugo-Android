package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.viewholder.FollowerFollowingViewHolder;
import market.zy.com.myapplication.entity.user.follow.Follower;

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
    public void onBindViewHolder(FollowerFollowingViewHolder holder, int position) {
        holder.mUsername.setText(mData.get(position).getUserName());
        holder.mUserTag.setText(mData.get(position).getAboutMe());
        Glide.with(mContext)
                .load(mData.get(position).getUserAvatar())
                .crossFade()
                .centerCrop()
                .into(holder.mUserAvatar);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
