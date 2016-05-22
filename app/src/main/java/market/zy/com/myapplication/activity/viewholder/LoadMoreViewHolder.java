package market.zy.com.myapplication.activity.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-20.
 */
public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.load_more_progressbar)
    public CircleProgressBar mCircleProgressBar;

    @Bind(R.id.no_more)
    public TextView mNoMoreText;

    public LoadMoreViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
