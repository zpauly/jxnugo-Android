package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;

/**
 * Created by dell on 2016/3/11.
 */
public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.MyViewHolder> {
    private Context mContext;
    private View mView;

    public PersonalAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.personal_recyclerview_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(R.drawable.ic_keyboard_arrow_right_24dp)
                .into(holder.mImageView);
        switch (position) {
            case 0 :
                holder.mTextView.setText(R.string.my_post);
                break;
            case 1 :
                holder.mTextView.setText(R.string.my_collection);
                break;
            case 2 :
                holder.mTextView.setText(R.string.my_draft);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.personal_item_textview)
        protected TextView mTextView;

        @Bind(R.id.personal_item_arrow_right)
        protected ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
