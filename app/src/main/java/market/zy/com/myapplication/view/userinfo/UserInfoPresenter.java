package market.zy.com.myapplication.view.userinfo;

import android.content.Context;

import market.zy.com.myapplication.adapter.recyclerviewAdapter.FollowerFollowingAdapter;
import market.zy.com.myapplication.adapter.recyclerviewAdapter.PostCollectionAdapter;
import market.zy.com.myapplication.db.post.PostDetailDao;
import market.zy.com.myapplication.db.user.UserInfoDao;
import market.zy.com.myapplication.db.user.UserInfoModel;
import market.zy.com.myapplication.entity.post.OneSimplePost;
import market.zy.com.myapplication.entity.post.collection.CollectionPosts;
import market.zy.com.myapplication.entity.post.user.UserPosts;
import market.zy.com.myapplication.entity.user.follow.UserFollowed;
import market.zy.com.myapplication.entity.user.follow.UserFollowers;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-6.
 */
public class UserInfoPresenter implements UserInfoContract.Presenter {
    private UserInfoContract.View mUserinfoView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;
    private UserInfoModel userInfo;
    private String auth;

    private int userId;

    private Subscriber<CollectionPosts> collectionPostsSubscriber;
    private Subscriber<UserPosts> userPostsSubscriber;
    private Subscriber<UserFollowed> followedSubscirber;
    private Subscriber<UserFollowers> followersSubscriber;

    public UserInfoPresenter(UserInfoContract.View view, Context context, int userId) {
        mUserinfoView = view;
        mContext = context;
        this.userId = userId;
        mUserinfoView.setPresenter(this);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
        userInfo = UserInfoDao.queryUserInfo();
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
    }

    @Override
    public void stop() {
        if (collectionPostsSubscriber != null) {
            if (collectionPostsSubscriber.isUnsubscribed()) {
                collectionPostsSubscriber.unsubscribe();
            }
        }
        if (userPostsSubscriber != null) {
            if (userPostsSubscriber.isUnsubscribed()) {
                userPostsSubscriber.unsubscribe();
            }
        }
        if (followedSubscirber != null) {
            if (followedSubscirber.isUnsubscribed()) {
                followedSubscirber.unsubscribe();
            }
        }
        if (followersSubscriber != null) {
            if (followersSubscriber.isUnsubscribed()) {
                followersSubscriber.unsubscribe();
            }
        }
    }

    @Override
    public void loadPostsData(final PostCollectionAdapter adapter) {
        userPostsSubscriber = new Subscriber<UserPosts>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(UserPosts userPosts) {
                adapter.addAllData(userPosts.getUserPosts());
                for (OneSimplePost post : userPosts.getUserPosts()) {
                    PostDetailDao.insertPostDetail(post);
                }
            }
        };
        JxnuGoNetMethod.getInstance().getUserPosts(userPostsSubscriber, auth, userId);
    }

    @Override
    public void loadCollectionData(final PostCollectionAdapter adapter) {
        collectionPostsSubscriber = new Subscriber<CollectionPosts>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(CollectionPosts collectionPosts) {
                adapter.addAllData(collectionPosts.getCollectionPost());
                for (OneSimplePost post : collectionPosts.getCollectionPost()) {
                    PostDetailDao.insertPostDetail(post);
                }
            }
        };
        JxnuGoNetMethod.getInstance().getCollectionPosts(collectionPostsSubscriber, auth, userId);
    }

    @Override
    public void loadFollowersData(final FollowerFollowingAdapter adapter) {
        followersSubscriber = new Subscriber<UserFollowers>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserFollowers userFollowers) {
                adapter.addAllData(userFollowers.getFollowers());
            }
        };
        JxnuGoNetMethod.getInstance().getUserFollowers(followersSubscriber, auth, userId);
    }

    @Override
    public void loadFollowedData(final FollowerFollowingAdapter adapter) {
        followedSubscirber = new Subscriber<UserFollowed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserFollowed userFollowed) {
                adapter.addAllData(userFollowed.getFollowed());
            }
        };
        JxnuGoNetMethod.getInstance().getUserFollowed(followedSubscirber, auth, userId);
    }
}
