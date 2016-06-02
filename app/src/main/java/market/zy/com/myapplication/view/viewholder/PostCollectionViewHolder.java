package market.zy.com.myapplication.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-26.
 */
public class PostCollectionViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.post_collection_delete_imageview)
    public ImageView mDeleteImageView;

    @Bind(R.id.post_collection_delete_layout)
    public LinearLayout mDeleteLayout;

    @Bind(R.id.post_collection_layout)
    public LinearLayout mLayout;

    @Bind(R.id.post_collection_imageview)
    public ImageView mCoverImageView;

    @Bind(R.id.post_collection_title)
    public TextView mTitleText;

    @Bind(R.id.post_collection_time)
    public TextView mTimeText;

    public PostCollectionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
//        mDeleteImageView = (ImageView) itemView.findViewById(R.id.post_collection_delete_imageview);
//        mDeleteLayout = (FrameLayout) itemView.findViewById(R.id.post_collection_delete_layout);

    }
}
