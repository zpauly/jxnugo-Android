package market.zy.com.myapplication.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-28.
 */
public class FollowerFollowingViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.follow_user_avatar)
    public CircleImageView mUserAvatar;

    @Bind(R.id.follow_username)
    public TextView mUsername;

    @Bind(R.id.follow_user_tag)
    public TextView mUserTag;

    public FollowerFollowingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
