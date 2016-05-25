package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;

/**
 * Created by dell on 2016/3/14.
 */
public class ClassifyNavAdapter extends RecyclerView.Adapter<ClassifyNavAdapter.MyViewHolder> {
    private String[] mData;
    private Context mContext;
    private View mView;
    private MyViewHolder viewHolder;
    private ViewGroup mParent;

    private int selected = -1;


    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }



    public ClassifyNavAdapter(Context context) {
        mContext = context;
        mData = mContext.getResources().getStringArray(R.array.classify);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item_classify_nav, parent, false);
        viewHolder = new MyViewHolder(mView);
        mParent = parent;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTextView.setText(mData[position]);
        if (mOnItemClickListener != null) {
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.mTextView);
                    selected = position;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.kinds)
        protected TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
