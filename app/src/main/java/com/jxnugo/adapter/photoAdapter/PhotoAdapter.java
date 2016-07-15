package com.jxnugo.adapter.photoAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import com.jxnugo.Constants;
import com.jxnugo.db.post.PhotoModel;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by zpauly on 16-5-21.
 */
public class PhotoAdapter extends PagerAdapter {
    private Context mContext;

    private List<PhotoModel> mData = new ArrayList<>();

    public PhotoAdapter(Context context, List<PhotoModel> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mContext);

        Glide.with(mContext)
                .load(Uri.parse(Constants.PIC_BASE_URL + mData.get(position).getKey()))
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
