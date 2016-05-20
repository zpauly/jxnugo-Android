package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.viewholder.CommentsViewHolder;
import market.zy.com.myapplication.engine.ImageEngine;
import market.zy.com.myapplication.entity.comments.Comment;

/**
 * Created by zpauly on 16-5-20.
 */
public class PostDetailCommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {
    private Context mContext;

    private List<Comment> mData = new ArrayList<>();

    public PostDetailCommentsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.comments_recyclerview_item, parent, false);
        CommentsViewHolder viewHolder = new CommentsViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        holder.mUsername.setText(mData.get(position).getAuthor());
        holder.mCommentsTime.setText(mData.get(position).getTimestamp());
        holder.mComments.setText(mData.get(position).getBody());
        ImageEngine.loadimageFromUrl(mContext, holder.mUserAvatar, mData.get(position).getAuthorAvatar());
    }

    @Override
    public int getItemCount() {
        return mData.size() > 3 ? 3 : mData.size();
    }
}
