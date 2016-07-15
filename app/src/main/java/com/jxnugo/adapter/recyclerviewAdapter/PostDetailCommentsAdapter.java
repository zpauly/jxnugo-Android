package com.jxnugo.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import com.jxnugo.R;
import com.jxnugo.view.viewholder.CommentsViewHolder;
import com.jxnugo.entity.post.comments.Comment;

/**
 * Created by zpauly on 16-5-20.
 */
public class PostDetailCommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {
    private Context mContext;

    private List<Comment> mData = new ArrayList<>();

    public PostDetailCommentsAdapter(Context context) {
        mContext = context;
    }

    public void addData(Comment data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    public void swapData(List<Comment> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addAllData(List<Comment> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_comments, parent, false);
        CommentsViewHolder viewHolder = new CommentsViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        holder.mUsername.setText(mData.get(position).getAuthor());
        holder.mCommentsTime.setText(mData.get(position).getTimestamp());
        holder.mComments.setText(mData.get(position).getBody());
        Glide.with(mContext)
                .load(mData.get(position).getAuthorAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(holder.mUserAvatar);
    }

    @Override
    public int getItemCount() {
        return mData.size() > 3 ? 3 : mData.size();
    }
}
