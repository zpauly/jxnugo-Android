package market.zy.com.myapplication.adapter.recyclerviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.userinfo.UserinfoActivity;
import market.zy.com.myapplication.activity.viewholder.PersonalViewHolder;

/**
 * Created by zpauly on 2016/3/11.
 */
public class PersonalAdapter extends RecyclerView.Adapter<PersonalViewHolder> {
    private Context mContext;
    private View mView;

    private int userId;

    private boolean isSelf;

    public PersonalAdapter(Context context, boolean isSelf, int id) {
        mContext = context;
        userId = id;
        this.isSelf = isSelf;
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
        final Intent intent = new Intent();
        intent.setClass(mContext, UserinfoActivity.class);
        if (isSelf == true) {
            switch (position) {
                case 0:
                    holder.mTextView.setText(R.string.my_post);
                    holder.mImageView.setImageResource(R.drawable.ic_folder_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_POST);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    holder.mTextView.setText(R.string.my_collection);
                    holder.mImageView.setImageResource(R.drawable.ic_drafts_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_COLLECTION);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    holder.mTextView.setText(R.string.my_following);
                    holder.mImageView.setImageResource(R.drawable.ic_visibility_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_FOLLOWING);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    holder.mTextView.setText(R.string.my_followers);
                    holder.mImageView.setImageResource(R.drawable.ic_people_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_FOLLOWERS);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        } else {
            switch (position) {
                case 0 :
                    holder.mTextView.setText(R.string.other_post);
                    holder.mImageView.setImageResource(R.drawable.ic_folder_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_POST);
                            intent.putExtra(UserinfoActivity.USER_ID, userId);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 1 :
                    holder.mTextView.setText(R.string.other_collection);
                    holder.mImageView.setImageResource(R.drawable.ic_drafts_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_COLLECTION);
                            intent.putExtra(UserinfoActivity.USER_ID, userId);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 2 :
                    holder.mTextView.setText(R.string.other_following);
                    holder.mImageView.setImageResource(R.drawable.ic_visibility_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_FOLLOWING);
                            intent.putExtra(UserinfoActivity.USER_ID, userId);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 3 :
                    holder.mTextView.setText(R.string.other_followers);
                    holder.mImageView.setImageResource(R.drawable.ic_people_black_24dp);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra(UserinfoActivity.USERINFO_PAGE, UserinfoActivity.MY_FOLLOWERS);
                            intent.putExtra(UserinfoActivity.USER_ID, userId);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
