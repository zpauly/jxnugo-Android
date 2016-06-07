package market.zy.com.myapplication.presenter.trade;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.post.OnePagePost;

/**
 * Created by zpauly on 16-6-7.
 */
public class TradeContract {
    public interface Presenter extends BasePresenter {
        void loadNewData();

        void loadMoreData();

        void loadNewDataByTag(int tagSelected);

        void loadMoreDataByTag(int tagSelected);

        void searchNewData(String query);

        void searchMoreData();
    }

    public interface View extends BaseView<Presenter> {
        void addNewData(OnePagePost onePagePost);

        void addMoreData(OnePagePost onePagePost);

        void addTagNewData(OnePagePost onePagePost);

        void addTagMoreData(OnePagePost onePagePost);

        void addSearchNewData(OnePagePost onePagePost);

        void addSearchMoreData(OnePagePost onePagePost);

        void stopRefreshing();

        void showPleaseLogin();
    }
}
