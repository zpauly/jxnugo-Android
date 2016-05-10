package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.postcontent.PostContentActivity;
import market.zy.com.myapplication.engine.tradelist.Goods;

/**
 * Created by dell on 2016/3/12.
 */
public class TradeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private View mView;

    private List<Goods> mData = new ArrayList<>();

    private boolean showLoadMore = false;

    public TradeListAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < 20; i++) {
            mData.add(new Goods());
        }
    }

    public void addData(Goods goods) {
        mData.add(goods);
        notifyDataSetChanged();
    }

    public void addAllData(List<Goods> list) {
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
        if (position == getItemCount() - 1)
            return -1;
        else
            return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            mView = LayoutInflater.from(mContext)
                    .inflate(R.layout.loadmore_layout, parent, false);
            MyViewHolder2 viewHolder = new MyViewHolder2(mView);
            return viewHolder;
        } else {
            mView = LayoutInflater.from(mContext)
                    .inflate(R.layout.trade_list_recyclerview_item, parent, false);
            MyViewHolder1 viewHolder = new MyViewHolder1(mView);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (showLoadMore && position == getItemCount() - 1){
            MyViewHolder2 viewHolder = (MyViewHolder2) holder;
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
        MyViewHolder1 viewHolder = (MyViewHolder1) holder;
        if (position % 2 == 0)
            Glide.with(mContext).load(R.mipmap.cheese_1).centerCrop().into(viewHolder.mImageView);
        else
            Glide.with(mContext).load(R.mipmap.cheese_3).centerCrop().into(viewHolder.mImageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postContentIntent = new Intent();
                postContentIntent.setClass(mContext, PostContentActivity.class);
                mContext.startActivity(postContentIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }


    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        @Bind(R.id.trade_list_image)
        protected ImageView mImageView;

        @Bind(R.id.trade_list_text)
        protected TextView mTextView;

        public MyViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @Bind(R.id.load_more_progressbar)
        protected CircleProgressBar mCircleProgressBar;
        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
