package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.postcontent.PostContentActivity;
import market.zy.com.myapplication.entity.post.OneSimplePost;
import market.zy.com.myapplication.utils.ImageUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 2016/3/12.
 */
public class TradeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private View mView;

    private List<OneSimplePost> mData = new ArrayList<>();

    private boolean showLoadMore = false;

    public TradeListAdapter(Context context) {
        mContext = context;
        /*for (int i = 0; i < 0; i++) {
            mData.add(new OneSimplePost());
        }*/
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
        if (position == getItemCount() - 1){
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
        loadAvatar(viewHolder.mAvatar, mData.get(position).getPostUserAvator());
        /*loadCover(viewHolder.mImageView, mData.get(position).getPhotos().get(0).getKey());*/
        viewHolder.mUsername.setText(mData.get(position).getPostUserName());
        viewHolder.mGoodname.setText(mData.get(position).getPhotos().get(0).getKey());
        viewHolder.mTime.setText(mData.get(position).getTimestamp());
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

    private void loadAvatar(final CircleImageView avatar, final String url) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = ImageUtil.getBitmapFromURL(url);
                subscriber.onNext(bitmap);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Glide.with(mContext)
                                .load(bitmap)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(avatar);
                    }
                });
    }

    private void loadCover(final ImageView cover, final String url) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = ImageUtil.getBitmapFromURL(url);
                subscriber.onNext(bitmap);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Glide.with(mContext)
                                .load(bitmap)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(cover);
                    }
                });
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        @Bind(R.id.trade_list_image)
        protected ImageView mImageView;

        @Bind(R.id.trade_list_item_goodname)
        protected TextView mGoodname;

        @Bind(R.id.trade_list_item_username)
        protected TextView mUsername;

        @Bind(R.id.trade_list_item_avatar)
        protected CircleImageView mAvatar;

        @Bind(R.id.trade_list_item_time)
        protected TextView mTime;

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
