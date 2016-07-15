package com.jxnugo.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.jxnugo.R;

/**
 * Created by zpauly on 16-5-31.
 */
public class ClassifyLabelViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.classify_label)
    public TextView mLabel;

    public ClassifyLabelViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
