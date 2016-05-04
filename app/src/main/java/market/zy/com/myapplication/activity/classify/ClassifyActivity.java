package market.zy.com.myapplication.activity.classify;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.ClassifyNavAdapter;
import market.zy.com.myapplication.ui.support.DividerItemDecoration;

/**
 * Created by dell on 2016/3/14.
 */
public class ClassifyActivity extends BaseActivity {
    @Bind(R.id.classify_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.classify_nav_recyclerview)
    protected RecyclerView mNavRecyclerView;

    private ClassifyNavAdapter mNavAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);

        setOnBackTwiceToTrue();

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        setUpToolbar();

        setUpRecyclerView();
    }

    private void setUpToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.classify);
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void setUpRecyclerView() {
        mNavAdapter = new ClassifyNavAdapter(this);
        mNavRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNavRecyclerView.setAdapter(mNavAdapter);
        mNavRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        setUpAdapter();
    }

    private void setUpAdapter() {

    }
}
