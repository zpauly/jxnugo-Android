package market.zy.com.myapplication.view.userinfo;

import market.zy.com.myapplication.adapter.recyclerviewAdapter.FollowerFollowingAdapter;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostCollectionAdapter;
import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;

/**
 * Created by zpauly on 16-6-6.
 */
public class UserInfoContract {
    interface Presenter extends BasePresenter {
        void loadPostsData(PostCollectionAdapter adapter);

        void loadCollectionData(PostCollectionAdapter adapter);

        void loadFollowersData(FollowerFollowingAdapter adapter);

        void loadFollowedData(FollowerFollowingAdapter adapter);
    }

    interface View extends BaseView<Presenter> {

    }
}
