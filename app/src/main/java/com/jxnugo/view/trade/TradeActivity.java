package com.jxnugo.view.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.jxnugo.Constants;
import com.jxnugo.R;
import com.jxnugo.view.NoInternetFragment;
import com.jxnugo.view.publish.PublishGoodsActivity;
import com.jxnugo.listener.OnSearchListener;
import com.jxnugo.ui.material.MaterialDrawerActivity;
import com.jxnugo.utils.MonitorUtil;

/**
 * Created by zpauly on 2016/3/10.
 */
public class TradeActivity extends MaterialDrawerActivity {
    public static final String POST_ID = "POST_ID";
    public static final String POST_COVER = "POST_COVER";

    @Bind(R.id.trade_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.search_view)
    protected MaterialSearchView mSearchView;

    private Fragment mTradeFragment;

    private FragmentManager mContextFragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private FragmentManager mMainFM;
    private FragmentTransaction mMainFT;

    private OnSearchListener onSearchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        initView();

        initDrawer(this, mToolbar, Constants.TRADEACTIVITY);

        setupWindowEnterFadeAnimations();
        setupWindowExitFadeAnimations();
    }

    public void setOnSearchListener(OnSearchListener listener) {
        onSearchListener = listener;
    }

    private void initView() {
        ButterKnife.bind(this);
        setUpToolbar();
        setUpSearchView();

        setUpFragment();
    }

    private void setUpFragment() {
        mMainFM = getSupportFragmentManager();
        mMainFT = mMainFM.beginTransaction();

        if (MonitorUtil.isNetConnected(this)) {
            mTradeFragment = new TradeFragment();
        } else {
            mTradeFragment = new NoInternetFragment();
        }
        mMainFT.replace(R.id.trade_fragment, mTradeFragment);
        mMainFT.commit();
    }

    private void setUpSearchView() {
        mSearchView.setVoiceSearch(true);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (onSearchListener != null) {
                    return onSearchListener.onTextSubmit(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (onSearchListener != null) {
                    return onSearchListener.onTextChange(newText);
                }
                return false;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                if (onSearchListener != null) {
                    onSearchListener.onShown();
                }
            }

            @Override
            public void onSearchViewClosed() {
                if (onSearchListener != null) {
                    onSearchListener.onClosed();
                }
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
