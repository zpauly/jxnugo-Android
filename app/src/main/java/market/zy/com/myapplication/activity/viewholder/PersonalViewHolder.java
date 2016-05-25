package market.zy.com.myapplication.activity.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-20.
 */
public class PersonalViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.personal_item_textview)
    public TextView mTextView;

    @Bind(R.id.personal_item_imageview)
    public ImageView mImageView;

    public PersonalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
