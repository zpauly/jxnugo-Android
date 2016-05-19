package market.zy.com.myapplication.activity.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PersonalAdapter;
import market.zy.com.myapplication.ui.support.DividerItemDecoration;

/**
 * Created by zpauly on 16-5-18.
 */
public class UserDetailFragment extends Fragment {

    private View mView;

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.personal_details_fragment, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.personal_detail_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        mRecyclerView.setAdapter(new PersonalAdapter(getContext()));

        return mView;
    }


}
