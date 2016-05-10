package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.entity.Comment;

/**
 * Created by root on 16-5-10.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private List<Comment> mData = new ArrayList<>();

    public CommentsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView1 = LayoutInflater.from(mContext).inflate(R.layout.comments_recyclerview_item, parent, false);
        View mView2 = LayoutInflater.from(mContext).inflate(R.layout.loadmore_layout, parent, false);
        RecyclerView.ViewHolder viewHolder;
        if (viewType == -1) {
            viewHolder = new MyViewHolder2(mView2);
        } else {
            viewHolder = new MyViewHolder1(mView1);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == -1) {
            MyViewHolder2 holder2 = (MyViewHolder2) holder;
            holder2.mCircleProgressBar.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light, android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            return;
        }
        MyViewHolder1 myViewHolder1 = (MyViewHolder1) holder;
        if (mData.size() == 0) {
            myViewHolder1.mUserTextView.setText("username");
            myViewHolder1.mCommentTextView.setText("comments");
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return -1;
        else
            return super.getItemViewType(position);
    }

    public void addData(Comment comment) {
        mData.add(comment);
        notifyDataSetChanged();
    }

    public void addAllData(List<Comment> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {

        @Bind(R.id.comments_item_avater)
        CircleImageView mAvaterImage;

        @Bind(R.id.comments_item_user)
        TextView mUserTextView;

        @Bind(R.id.comments_item_comment)
        TextView mCommentTextView;

        public MyViewHolder1(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        @Bind(R.id.load_more_progressbar)
        protected CircleProgressBar mCircleProgressBar;
        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
