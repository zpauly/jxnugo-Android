package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.view.viewholder.ClassifyLabelViewHolder;
import market.zy.com.myapplication.listener.OnItemClickListener;

/**
 * Created by zpauly on 16-5-31.
 */
public class ClassifyLabelAdapter extends RecyclerView.Adapter<ClassifyLabelViewHolder> {
    private Context mContext;

    private OnItemClickListener onItemClickListener;

    public ClassifyLabelAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ClassifyLabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_classify_label, parent, false);
        ClassifyLabelViewHolder viewHolder = new ClassifyLabelViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClassifyLabelViewHolder holder, final int position) {
        switch (position) {
            case 0 : // 0生活用品
                holder.mLabel.setText(mContext.getResources().getStringArray(R.array.goods_types)[0]);
                holder.mLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                break;
            case 1 : // 1数码科技
                holder.mLabel.setText(mContext.getResources().getStringArray(R.array.goods_types)[1]);
                holder.mLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                break;
            case 2 : // 2服饰箱包
                holder.mLabel.setText(mContext.getResources().getStringArray(R.array.goods_types)[2]);
                holder.mLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                break;
            case 3 : // 3图书音像
                holder.mLabel.setText(mContext.getResources().getStringArray(R.array.goods_types)[3]);
                holder.mLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                break;
            case 4 : // 4其它
                holder.mLabel.setText(mContext.getResources().getStringArray(R.array.goods_types)[4]);
                holder.mLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                break;
            case 5 : // 4其它
                holder.mLabel.setText(mContext.getString(R.string.all));
                holder.mLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
