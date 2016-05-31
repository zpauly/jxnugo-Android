package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
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
import market.zy.com.myapplication.activity.trade.TradeActivity;
import market.zy.com.myapplication.activity.viewholder.ClassifyLabelViewHolder;
import market.zy.com.myapplication.activity.viewholder.ClassifyLayoutViewHolder;
import market.zy.com.myapplication.activity.viewholder.LoadMoreViewHolder;
import market.zy.com.myapplication.activity.viewholder.TradeListViewHolder;
import market.zy.com.myapplication.adapter.OnItemClickListener;
import market.zy.com.myapplication.entity.post.OneSimplePost;
import market.zy.com.myapplication.utils.SPUtil;

/**
 * Created by zpauly on 2016/3/12.
 */
public class TradeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private View mView;

    private List<OneSimplePost> mData = new ArrayList<>();

    private boolean showLoadMore = false;

    private OnItemClickListener onItemClickListener;

    public TradeListAdapter(Context context) {
        mContext = context;
    }

    public void setOnLabelItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addData(OneSimplePost post) {
        mData.add(post);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void addAllData(List<OneSimplePost> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void setShowLoadMore(boolean show) {
        showLoadMore = show;
        notifyDataSetChanged();
    }

    public boolean isShowingLoadMore() {
        return showLoadMore;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        if (position == getItemCount() - 1)
            return -1;
        else
            return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            mView = LayoutInflater.from(mContext)
                    .inflate(R.layout.classify_label_layout, parent, false);
            ClassifyLayoutViewHolder viewHolder = new ClassifyLayoutViewHolder(mView);
            return viewHolder;
        }
        if (viewType == -1) {
            mView = LayoutInflater.from(mContext)
                    .inflate(R.layout.loadmore_layout, parent, false);
            LoadMoreViewHolder viewHolder = new LoadMoreViewHolder(mView);
            return viewHolder;
        } else {
            mView = LayoutInflater.from(mContext)
                    .inflate(R.layout.recyclerview_item_trade_list, parent, false);
            TradeListViewHolder viewHolder = new TradeListViewHolder(mView);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ClassifyLabelAdapter adapter = new ClassifyLabelAdapter(mContext);
            ClassifyLayoutViewHolder viewHolder = (ClassifyLayoutViewHolder) holder;
            viewHolder.mClassifyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            viewHolder.mClassifyRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, position);
                    }
                }
            });
            return;
        }
        if (position == getItemCount() - 1){
            LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
            viewHolder.mCircleProgressBar.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light, android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            if (showLoadMore) {
                viewHolder.itemView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.itemView.setVisibility(View.GONE);
            }
            return;
        }
        final int pos = position - 1;
        TradeListViewHolder viewHolder = (TradeListViewHolder) holder;
        Glide.with(mContext)
                .load(Constants.PIC_BASE_URL +
                        mData.get(pos).getPhotos().get(0).getKey())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(viewHolder.mImageView);
        Glide.with(mContext)
                .load(mData.get(pos).getPostUserAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(viewHolder.mAvatar);
        viewHolder.mUsername.setText(mData.get(pos).getPostNickName());
        viewHolder.mGoodname.setText(mData.get(pos).getGoodsName());
        viewHolder.mTime.setText(mData.get(pos).getTimestamp());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtil.getInstance(mContext).getCurrentUsername() == null) {
                    Snackbar.make(holder.itemView, R.string.please_login, Snackbar.LENGTH_SHORT).show();
                } else {
                    Intent postDetailsIntent = new Intent();
                    postDetailsIntent.putExtra(TradeActivity.POST_ID, mData.get(pos).getPostId());
                    postDetailsIntent.putExtra(TradeActivity.POST_COVER, mData.get(pos).getPhotos().get(0).getKey());
                    postDetailsIntent.setClass(mContext, PostDetailsActivity.class);
                    mContext.startActivity(postDetailsIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size() + 2;
    }
}
