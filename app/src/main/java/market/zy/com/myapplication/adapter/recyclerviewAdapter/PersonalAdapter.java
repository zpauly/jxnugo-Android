package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.viewholder.PersonalViewHolder;

/**
 * Created by zpauly on 2016/3/11.
 */
public class PersonalAdapter extends RecyclerView.Adapter<PersonalViewHolder> {
    private Context mContext;
    private View mView;

    public PersonalAdapter(Context context) {
        mContext = context;
    }

    @Override
    public PersonalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item_personal, parent, false);
        PersonalViewHolder viewHolder = new PersonalViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonalViewHolder holder, int position) {
        switch (position) {
            case 0 :
                holder.mTextView.setText(R.string.my_post);
                holder.mImageView.setImageResource(R.drawable.ic_folder_black_24dp);
                break;
            case 1 :
                holder.mTextView.setText(R.string.my_collection);
                holder.mImageView.setImageResource(R.drawable.ic_drafts_black_24dp);
                break;
            case 2 :
                holder.mTextView.setText(R.string.my_following);
                holder.mImageView.setImageResource(R.drawable.ic_visibility_black_24dp);
                break;
            case 3 :
                holder.mTextView.setText(R.string.my_followers);
                holder.mImageView.setImageResource(R.drawable.ic_people_black_24dp);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
