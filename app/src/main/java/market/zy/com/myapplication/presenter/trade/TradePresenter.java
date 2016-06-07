package market.zy.com.myapplication.presenter.trade;

import android.content.Context;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.db.post.PhotosDao;
import market.zy.com.myapplication.db.post.PostDetailDao;
import market.zy.com.myapplication.entity.post.OnePagePost;
import market.zy.com.myapplication.entity.post.OneSimplePost;
import market.zy.com.myapplication.entity.post.PhotoKey;
import market.zy.com.myapplication.entity.post.search.SearchKeyWords;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-7.
 */
public class TradePresenter implements TradeContract.Presenter {
    private TradeContract.View mTradeView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;

    private Subscriber<OnePagePost> newSubscriber;
    private Subscriber<OnePagePost> moreSubscriber;
    private Subscriber<OnePagePost> tagSubscriber;
    private Subscriber<OnePagePost> searchSubscriber;

    private int postIdToLoad;

    public TradePresenter(TradeContract.View view, Context context) {
        mTradeView = view;
        mContext = context;
        mTradeView.setPresenter(this);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
    }

    @Override
    public void stop() {
        if (newSubscriber != null) {
            if (newSubscriber.isUnsubscribed()) {
                newSubscriber.unsubscribe();
            }
        }
        if (moreSubscriber != null) {
            if (moreSubscriber.isUnsubscribed()) {
                moreSubscriber.unsubscribe();
            }
        }
        if (tagSubscriber != null) {
            if (tagSubscriber.isUnsubscribed()) {
                tagSubscriber.unsubscribe();
            }
        }
        if (searchSubscriber != null) {
            if (searchSubscriber.isUnsubscribed()) {
                searchSubscriber.unsubscribe();
            }
        }
    }

    @Override
    public void loadNewData() {
        newSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
                mTradeView.stopRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                mTradeView.stopRefreshing();
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                mTradeView.addNewData(onePagePost);
            }
        };
        postIdToLoad = 1;
        JxnuGoNetMethod.getInstance()
                .getOnePagePosts(newSubscriber, postIdToLoad);
    }

    @Override
    public void loadMoreData() {
        moreSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
                mTradeView.stopRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                mTradeView.addMoreData(onePagePost);
            }
        };
        JxnuGoNetMethod.getInstance()
                .getOnePagePosts(moreSubscriber, ++postIdToLoad);
    }

    @Override
    public void loadNewDataByTag(int tagSelected) {
        tagSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
                mTradeView.stopRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                mTradeView.stopRefreshing();
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                mTradeView.addTagNewData(onePagePost);
            }
        };
        postIdToLoad = 1;
        JxnuGoNetMethod.getInstance().getPostsByTag(tagSubscriber, tagSelected, postIdToLoad);
    }

    @Override
    public void loadMoreDataByTag(int tagSelected) {
        tagSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                mTradeView.addTagMoreData(onePagePost);
            }
        };
        JxnuGoNetMethod.getInstance().getPostsByTag(tagSubscriber, tagSelected, ++postIdToLoad);
    }

    @Override
    public void searchNewData(String query) {
        searchSubscriber = new Subscriber<OnePagePost>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(OnePagePost onePagePost) {
                mTradeView.addSearchNewData(onePagePost);
            }
        };
        if (SPUtil.getInstance(mContext) == null) {
            mTradeView.showPleaseLogin();
            return;
        }
        String auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
        SearchKeyWords keyWords = new SearchKeyWords();
        keyWords.setKeyWords(query);
        JxnuGoNetMethod.getInstance().searchPosts(searchSubscriber, auth, keyWords);
    }

    @Override
    public void searchMoreData() {

    }
}
