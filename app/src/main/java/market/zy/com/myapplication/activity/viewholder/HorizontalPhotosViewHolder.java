package market.zy.com.myapplication.activity.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-21.
 */
public class HorizontalPhotosViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.horizontal_photo_recyclerview_photo)
    public ImageView mImageView;

    public HorizontalPhotosViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
