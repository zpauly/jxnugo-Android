package com.jxnugo.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import com.jxnugo.R;

/**
 * Created by zpauly on 16-5-20.
 */
public class TradeListViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.trade_list_image)
    public ImageView mImageView;

    @Bind(R.id.trade_list_item_goodname)
    public TextView mGoodname;

    @Bind(R.id.trade_list_item_username)
    public TextView mUsername;

    @Bind(R.id.trade_list_item_avatar)
    public CircleImageView mAvatar;

    @Bind(R.id.trade_list_item_goodtime)
    public TextView mTime;

    public TradeListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
