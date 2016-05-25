package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.viewholder.CommentsViewHolder;
import market.zy.com.myapplication.activity.viewholder.LoadMoreViewHolder;
import market.zy.com.myapplication.entity.post.comments.Comment;

/**
 * Created by zpauly on 16-5-10.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private List<Comment> mData = new ArrayList<>();

    private boolean showLoadMore = false;

    public CommentsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView1 = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_comments, parent, false);
        View mView2 = LayoutInflater.from(mContext).inflate(R.layout.loadmore_layout, parent, false);
        RecyclerView.ViewHolder viewHolder;
        if (viewType == -1) {
            viewHolder = new LoadMoreViewHolder(mView2);
        } else {
            viewHolder = new CommentsViewHolder(mView1);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            LoadMoreViewHolder holder2 = (LoadMoreViewHolder) holder;
            holder2.mCircleProgressBar.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light, android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            holder2.mCircleProgressBar.setVisibility(View.GONE);
            holder2.mNoMoreText.setVisibility(View.VISIBLE);
            /*if (showLoadMore) {
                holder2.itemView.setVisibility(View.VISIBLE);
            } else {
                holder2.itemView.setVisibility(View.GONE);
            }*/

            return;
        }
        CommentsViewHolder myViewHolder1 = (CommentsViewHolder) holder;
        myViewHolder1.mUsername.setText(mData.get(position).getAuthor());
        myViewHolder1.mCommentsTime.setText(mData.get(position).getTimestamp());
        myViewHolder1.mComments.setText(mData.get(position).getBody());
        Glide.with(mContext)
                .load(mData.get(position).getAuthorAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(myViewHolder1.mUserAvatar);    }

    public void setShowLoadMore(boolean show) {
        showLoadMore = show;
        notifyDataSetChanged();
    }

    public boolean isShowingLoadMore() {
        return showLoadMore;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return -1;
        else
            return 1;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void addData(Comment comment) {
        mData.add(comment);
        notifyDataSetChanged();
    }

    public void addAllData(List<Comment> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
