package com.jxnugo.adapter.recyclerviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import com.jxnugo.Constants;
import com.jxnugo.R;
import com.jxnugo.view.post.PostDetailsActivity;
import com.jxnugo.view.trade.TradeActivity;
import com.jxnugo.view.viewholder.PostCollectionViewHolder;
import com.jxnugo.entity.post.OneSimplePost;

/**
 * Created by zpauly on 16-5-26.
 */
public class PostCollectionAdapter extends RecyclerView.Adapter<PostCollectionViewHolder> {
    private Context mContext;

    private List<OneSimplePost> mData = new ArrayList<>();

    public PostCollectionAdapter(Context context) {
        mContext = context;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void addData(OneSimplePost oneSimplePost) {
        mData.add(oneSimplePost);
        notifyDataSetChanged();
    }

    public void addAllData(List<OneSimplePost> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public PostCollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_my_post_collection, parent, false);
        PostCollectionViewHolder viewHolder = new PostCollectionViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostCollectionViewHolder holder, final int position) {
        holder.mTitleText.setText(mData.get(position).getGoodsName());
        holder.mTimeText.setText(mData.get(position).getTimestamp());
        Glide.with(mContext)
                .load(Constants.PIC_BASE_URL + mData.get(position).getPhotos().get(0).getKey())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(holder.mCoverImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, PostDetailsActivity.class);
                intent.putExtra(TradeActivity.POST_ID, mData.get(position).getPostId());
                intent.putExtra(TradeActivity.POST_COVER, mData.get(position).getPhotos().get(0).getKey());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
