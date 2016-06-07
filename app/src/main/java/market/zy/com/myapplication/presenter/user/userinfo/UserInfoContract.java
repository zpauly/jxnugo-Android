package market.zy.com.myapplication.presenter.user.userinfo;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.post.collection.CollectionPosts;
import market.zy.com.myapplication.entity.post.user.UserPosts;
import market.zy.com.myapplication.entity.user.follow.UserFollowed;
import market.zy.com.myapplication.entity.user.follow.UserFollowers;

/**
 * Created by zpauly on 16-6-6.
 */
public class UserInfoContract {
    public interface Presenter extends BasePresenter {
        void loadPostsData();

        void loadCollectionData();

        void loadFollowersData();

        void loadFollowedData();
    }

    public interface View extends BaseView<Presenter> {
        void addAllCollectionData(CollectionPosts collectionPosts);

        void addAllPostsData(UserPosts userPosts);

        void addAllFollowersData(UserFollowers userFollowers);

        void addAllFollowedData(UserFollowed userFollowed);
    }
}
