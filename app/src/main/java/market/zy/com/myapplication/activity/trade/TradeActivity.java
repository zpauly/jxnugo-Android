package market.zy.com.myapplication.activity.trade;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.classify.ClassifyActivity;
import market.zy.com.myapplication.activity.publish.PublishGoodsActivity;
import market.zy.com.myapplication.utils.sinaUtils.Constants;
import market.zy.com.myapplication.ui.material.MaterialDrawerActivity;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.TradeListAdapter;

/**
 * Created by dell on 2016/3/10.
 */
public class TradeActivity extends MaterialDrawerActivity {
    @Bind(R.id.trade_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.trade_list_recyclerview)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.trade_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.trade_appbar)
    protected AppBarLayout mAppbarLayout;

    @Bind(R.id.trade_move_to_top)
    protected ImageButton mUpArrow;

    @Bind(R.id.trade_move_to_bottom)
    protected ImageButton mDownArrow;

    @Bind(R.id.search_view)
    protected MaterialSearchView mSearchView;

    private LinearLayoutManager manager;

    private FragmentManager mContextFragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        initView();

        initDrawer(this, mToolbar, Constants.TRADEACTIVITY);

        setupWindowEnterFadeAnimations();
        setupWindowExitFadeAnimations();
    }

    private void initView() {
        ButterKnife.bind(this);
        setUpAppbarLayout();
        setUpToolbar();
        setUpRecyclerView();
        setUpRefreshLayout();
        setImageClick();
        setUpSearchView();
    }

    private void setUpSearchView() {
        mSearchView.setVoiceSearch(true);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchView.setSuggestions(getResources().getStringArray(R.array.demo));
                return false;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    private void setUpToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.trade_list);
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);

            setUpToolbarMenu();
        }
    }

    private void setUpRecyclerView() {
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new TradeListAdapter(this));
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (manager.findFirstCompletelyVisibleItemPosition() == 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    private void setUpAppbarLayout() {
        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0 && manager.findFirstCompletelyVisibleItemPosition() == 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    private void setUpRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    private void setImageClick() {
        mUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        mDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 2);
            }
        });
    }

    private void setUpToolbarMenu() {
        initMenuFragment();
    }

    private void initMenuFragment() {
        mContextFragmentManager = getSupportFragmentManager();
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.toolbar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View clickedView, int position) {
                switch (position) {
                    case 0 :
                        mMenuDialogFragment.dismiss();
                        break;
                    case 1 :
                        Intent publish = new Intent();
                        publish.setClass(TradeActivity.this, PublishGoodsActivity.class);
                        startActivity(publish);
                        break;
                    case 2 :
                        Intent classify = new Intent();
                        classify.setClass(TradeActivity.this, ClassifyActivity.class);
                        startActivity(classify);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.mipmap.ic_close_white_36dp);
        close.setBgResource(R.color.colorPrimaryDark);

        MenuObject publish = new MenuObject(getString(R.string.publish));
        publish.setResource(R.mipmap.ic_mode_edit_white_36dp);
        publish.setBgResource(R.color.colorPrimaryDark);

        MenuObject classify = new MenuObject(getString(R.string.classify));
        classify.setResource(R.mipmap.ic_sort_white_36dp);
        classify.setBgResource(R.color.colorPrimaryDark);

        menuObjects.add(close);
        menuObjects.add(publish);
        menuObjects.add(classify);
        return menuObjects;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trade_toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        mSearchView.setMenuItem(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.function :
                if (mContextFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(mContextFragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
            case R.id.search:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
