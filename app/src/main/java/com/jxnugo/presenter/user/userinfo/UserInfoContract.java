package com.jxnugo.presenter.user.userinfo;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;
import com.jxnugo.entity.post.collection.CollectionPosts;
import com.jxnugo.entity.post.user.UserPosts;
import com.jxnugo.entity.user.follow.UserFollowed;
import com.jxnugo.entity.user.follow.UserFollowers;

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
