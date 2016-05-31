package market.zy.com.myapplication.activity.trade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseFragment;
import market.zy.com.myapplication.adapter.OnItemClickListener;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.ClassifyLabelAdapter;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.TradeListAdapter;
import market.zy.com.myapplication.db.post.PhotosDao;
import market.zy.com.myapplication.db.post.PostDetailDao;
import market.zy.com.myapplication.entity.post.OnePagePost;
import market.zy.com.myapplication.entity.post.OneSimplePost;
import market.zy.com.myapplication.entity.post.PhotoKey;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import rx.Subscriber;

/**
 * Created by zpauly on 16-5-19.
 */
public class TradeFragment extends BaseFragment {
    private View mView;

    @Bind(R.id.trade_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.trade_list_recyclerview)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.trade_move_to_top)
    protected ImageButton mScrollToTopButton;

    @Bind(R.id.trade_move_to_bottom)
    protected ImageButton mScrollToBottomButton;

    private AppBarLayout mAppbarLayout;

    private LinearLayoutManager manager;
    private TradeListAdapter adapter;

    private int postIdToLoad = 1;
    private int tagSelected = -1;
    private boolean hasMore = true;

    private Subscriber<OnePagePost> newSubscriber;
    private Subscriber<OnePagePost> moreSubscriber;
    private Subscriber<OnePagePost> tagSubscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_trade, container, false);
        ButterKnife.bind(this, mView);

        initView();

        return mView;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        loadNewData();
    }*/

    @Override
    public void onPause() {
        unsubscribe();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    private void unsubscribe() {
        if (newSubscriber != null)
            newSubscriber.unsubscribe();
        if (moreSubscriber != null)
            moreSubscriber.unsubscribe();
        if (tagSubscriber != null) {
            tagSubscriber.unsubscribe();
        }
    }

    private void initView() {
        setUpAppbarLayout();
        setUpRefreshLayout();
        setUpRecyclerView();
        setImageClick();
    }

    private void setUpRefreshLayout() {
        mSwipeRefreshLayout.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
        mSwipeRefreshLayout.setRefreshing(true);
        loadNewData();
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (tagSelected != -1) {
                    loadNewDataByTag();
                } else {
                    loadNewData();
                }
            }
        });
    }

    private void setUpRecyclerView() {
        adapter = new TradeListAdapter(getActivity());
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnLabelItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0 :
                        tagSelected = 0;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadNewDataByTag();
                        break;
                    case 1 :
                        tagSelected = 1;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadNewDataByTag();
                        break;
                    case 2 :
                        tagSelected = 2;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadNewDataByTag();
                        break;
                    case 3 :
                        tagSelected = 3;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadNewDataByTag();
                        break;
                    case 4 :
                        tagSelected = 4;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadNewDataByTag();
                        break;
                    case 5 :
                        tagSelected = -1;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadNewData();
                        break;
                }
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (manager.findFirstCompletelyVisibleItemPosition() == 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisiableItemPosition = manager.findLastCompletelyVisibleItemPosition();
                if (lastVisiableItemPosition == adapter.getItemCount() - 2 && hasMore) {
                    adapter.setShowLoadMore(true);
                } else if (lastVisiableItemPosition == adapter.getItemCount() - 1
                        && adapter.isShowingLoadMore() && hasMore) {
                    adapter.setShowLoadMore(true);
                    if (tagSelected != -1) {
                        loadMoreDataByTag();
                    } else {
                        loadMoreData();
                    }
                } else {
                    adapter.setShowLoadMore(false);
                }
            }
        });
    }

    public void setUpAppbarLayout() {
        mAppbarLayout = (AppBarLayout) getActivity().findViewById(R.id.trade_appbar);
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

    private void setImageClick() {
        mScrollToTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        mScrollToBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 2);
            }
        });
    }

    private void setUpSearchView() {
        MaterialSearchView searchView = ((TradeActivity) getActivity()).mSearchView;
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    private void loadNewData() {
        newSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mSwipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                PhotosDao.deletePhotoBean();
                PostDetailDao.deletePostDetail();
                if (onePagePost.getPosts() == null) {
                    return;
                }
                for (OneSimplePost post : onePagePost.getPosts()) {
                    PostDetailDao.insertPostDetail(post);
                    for (PhotoKey key : post.getPhotos()) {
                        PhotosDao.insertPhotos(key, post.getPostId());
                    }
                }
                adapter.clearData();
                adapter.addAllData(onePagePost.getPosts());
                if (onePagePost.getNext() == null) {
                    hasMore = false;
                } else {
                    hasMore = true;
                }
            }
        };
        postIdToLoad = 1;
        JxnuGoNetMethod.getInstance()
                .getOnePagePosts(newSubscriber, postIdToLoad);
    }

    private void loadMoreData() {
        moreSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                if (onePagePost.getPosts() == null) {
                    return;
                }
                for (OneSimplePost post : onePagePost.getPosts()) {
                    PostDetailDao.insertPostDetail(post);
                    for (PhotoKey key : post.getPhotos()) {
                        PhotosDao.insertPhotos(key, post.getPostId());
                    }
                }
                adapter.addAllData(onePagePost.getPosts());
                if (onePagePost.getNext() == null) {
                    hasMore = false;
                } else {
                    hasMore = true;
                }
            }
        };
        JxnuGoNetMethod.getInstance()
                .getOnePagePosts(moreSubscriber, ++postIdToLoad);
    }

    private void loadNewDataByTag() {
        tagSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mSwipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                PhotosDao.deletePhotoBean();
                PostDetailDao.deletePostDetail();
                if (onePagePost.getPosts() == null) {
                    return;
                }
                for (OneSimplePost post : onePagePost.getPosts()) {
                    PostDetailDao.insertPostDetail(post);
                    for (PhotoKey key : post.getPhotos()) {
                        PhotosDao.insertPhotos(key, post.getPostId());
                    }
                }
                adapter.clearData();
                adapter.addAllData(onePagePost.getPosts());
                if (onePagePost.getNext() == null) {
                    hasMore = false;
                } else {
                    hasMore = true;
                }
            }
        };
        postIdToLoad = 1;
        JxnuGoNetMethod.getInstance().getPostsByTag(tagSubscriber, tagSelected, postIdToLoad);
    }

    private void loadMoreDataByTag() {
        tagSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                if (onePagePost.getPosts() == null) {
                    return;
                }
                for (OneSimplePost post : onePagePost.getPosts()) {
                    PostDetailDao.insertPostDetail(post);
                    for (PhotoKey key : post.getPhotos()) {
                        PhotosDao.insertPhotos(key, post.getPostId());
                    }
                }
                adapter.addAllData(onePagePost.getPosts());
                if (onePagePost.getNext() == null) {
                    hasMore = false;
                } else {
                    hasMore = true;
                }
            }
        };
        JxnuGoNetMethod.getInstance().getPostsByTag(tagSubscriber, tagSelected, ++postIdToLoad);
    }
}
