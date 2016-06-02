package market.zy.com.myapplication.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;

/**
 * Created by zpauly on 16-5-31.
 */
public class ClassifyLayoutViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.classify_recyclerview)
    public RecyclerView mClassifyRecyclerView;

    public ClassifyLayoutViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
