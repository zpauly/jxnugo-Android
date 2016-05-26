package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.viewholder.PostCollectionViewHolder;

/**
 * Created by zpauly on 16-5-26.
 */
public class PostCollectionAdapter extends RecyclerView.Adapter<PostCollectionViewHolder> {
    private Context mContext;

    public PostCollectionAdapter(Context context) {
        mContext = context;
    }

    @Override
    public PostCollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_my_post_collection, parent, false);
        PostCollectionViewHolder viewHolder = new PostCollectionViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostCollectionViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
