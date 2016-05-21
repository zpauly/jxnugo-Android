package market.zy.com.myapplication.adapter.recyclerviewAdapter;

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

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.post.PostDetailsActivity;
import market.zy.com.myapplication.activity.photos.PhotoActivity;
import market.zy.com.myapplication.activity.trade.TradeActivity;
import market.zy.com.myapplication.activity.viewholder.HorizontalPhotosViewHolder;
import market.zy.com.myapplication.db.post.PhotoBean;

/**
 * Created by zpauly on 16-5-21.
 */
public class PostDetailPhotosAdapter extends RecyclerView.Adapter<HorizontalPhotosViewHolder> {
    private Context mContext;

    private List<PhotoBean> photoKey = new ArrayList<>();

    public PostDetailPhotosAdapter(Context context) {
        mContext = context;
    }

    public void swapData(List<PhotoBean> list) {
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
