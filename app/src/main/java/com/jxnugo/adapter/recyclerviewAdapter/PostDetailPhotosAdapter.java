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
import com.jxnugo.view.photos.PhotoActivity;
import com.jxnugo.view.trade.TradeActivity;
import com.jxnugo.view.viewholder.HorizontalPhotosViewHolder;
import com.jxnugo.db.post.PhotoModel;

/**
 * Created by zpauly on 16-5-21.
 */
public class PostDetailPhotosAdapter extends RecyclerView.Adapter<HorizontalPhotosViewHolder> {
    private Context mContext;

    private List<PhotoModel> photoKey = new ArrayList<>();

    public PostDetailPhotosAdapter(Context context) {
        mContext = context;
    }

    public void swapData(List<PhotoModel> list) {
        photoKey.clear();
        photoKey.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public HorizontalPhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.horizontal_photo_recyclerview_item, parent, false);
        HorizontalPhotosViewHolder viewHolder = new HorizontalPhotosViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HorizontalPhotosViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(Constants.PIC_BASE_URL
                        + photoKey.get(position).getKey())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .fitCenter()
                .into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent();
                photoIntent.putExtra(TradeActivity.POST_ID, photoKey.get(position).getPostId());
                photoIntent.putExtra(PostDetailsActivity.CURRENT_PHOTO, position);
                photoIntent.setClass(mContext, PhotoActivity.class);
                mContext.startActivity(photoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoKey.size();
    }
}
