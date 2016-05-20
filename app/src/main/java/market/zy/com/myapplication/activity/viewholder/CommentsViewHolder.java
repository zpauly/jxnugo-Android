package market.zy.com.myapplication.activity.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-20.
 */
public class CommentsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.comments_item_avater)
    public CircleImageView mUserAvatar;

    @Bind(R.id.comments_item_user)
    public TextView mUsername;

    @Bind(R.id.comments_item_time)
    public TextView mCommentsTime;

    @Bind(R.id.comments_item_comment)
    public TextView mComments;

    public CommentsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
