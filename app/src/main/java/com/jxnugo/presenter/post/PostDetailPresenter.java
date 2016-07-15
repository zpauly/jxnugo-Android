package com.jxnugo.presenter.post;

import android.content.Context;

import com.jxnugo.db.post.PhotosDao;
import com.jxnugo.db.post.PostDetailDao;
import com.jxnugo.db.post.PostDetailModel;
import com.jxnugo.db.user.UserInfoDao;
import com.jxnugo.db.user.UserInfoModel;
import com.jxnugo.entity.post.collection.CollectPost;
import com.jxnugo.entity.post.collection.CollectStates;
import com.jxnugo.entity.post.collection.JudgeCollectPost;
import com.jxnugo.entity.post.collection.JudgeCollectStates;
import com.jxnugo.entity.post.collection.UncollectPost;
import com.jxnugo.entity.post.collection.UncollectStates;
import com.jxnugo.entity.post.comments.AllComments;
import com.jxnugo.entity.user.UserBasicInfo;
import com.jxnugo.network.JxnuGoNetMethod;
import com.jxnugo.utils.AuthUtil;
import com.jxnugo.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-6.
 */
public class PostDetailPresenter implements PostDetailContract.Presenter {
    private PostDetailContract.View mPostDetailView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;
    private String auth;
    private UserInfoModel userInfo;
    private PostDetailModel postDetail;
    private int postId;

    private Subscriber<AllComments> commentsSubscriber;
    private Subscriber<JudgeCollectStates> judgeCollectSubscriber;
    private Subscriber<CollectStates> collectSubscriber;
    private Subscriber<UncollectStates> uncollectSubsriber;
    private Subscriber<UserBasicInfo> publisherSubscriber;

    public PostDetailPresenter(PostDetailContract.View view, Context context, int postId) {
        mPostDetailView = view;
        mContext = context;
        this.postId = postId;
        mPostDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
        userInfo = UserInfoDao.queryUserInfo();
        postDetail = PostDetailDao.queryPostDetail(postId).get(0);
    }

    @Override
    public void stop() {
        if (commentsSubscriber != null) {
            if (commentsSubscriber.isUnsubscribed()) {
                commentsSubscriber.unsubscribe();
            }
        }
        if (judgeCollectSubscriber != null) {
            if (judgeCollectSubscriber.isUnsubscribed()) {
                judgeCollectSubscriber.unsubscribe();
            }
        }
        if (collectSubscriber != null) {
            if (collectSubscriber.isUnsubscribed()) {
                collectSubscriber.unsubscribe();
            }
        }
        if (uncollectSubsriber != null) {
            if (uncollectSubsriber.isUnsubscribed()) {
                uncollectSubsriber.unsubscribe();
            }
        }
        if (publisherSubscriber != null) {
            if (publisherSubscriber.isUnsubscribed()) {
                publisherSubscriber.unsubscribe();
            }
        }
    }

    @Override
    public void loadCommentsData() {
        commentsSubscriber = new Subscriber<AllComments>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllComments allComments) {
                mPostDetailView.swapCommentsData(allComments);
            }
        };
        netMethod.getAllComments(commentsSubscriber, auth, postId);
    }

    @Override
    public void loadPhotosData() {
        mPostDetailView.swapPhotosData(PhotosDao.queryPhoto(postId));
    }

    @Override
    public void isPostCollected() {
        judgeCollectSubscriber = new Subscriber<JudgeCollectStates>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JudgeCollectStates judgeCollectStates) {
                mPostDetailView.judgeCollectStates(judgeCollectStates);
            }
        };
        JudgeCollectPost post = new JudgeCollectPost();
        post.setPostId(postId);
        post.setUserId(userInfo.getUserId());
        netMethod.judgeCollectPost(judgeCollectSubscriber, auth, post);
    }

    @Override
    public void collect() {
        collectSubscriber = new Subscriber<CollectStates>() {
            @Override
            public void onCompleted() {
                mPostDetailView.collectSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mPostDetailView.collectFail();
            }

            @Override
            public void onNext(CollectStates collectStates) {

            }
        };
        CollectPost post = new CollectPost();
        post.setPostId(postId);
        post.setUserId(userInfo.getUserId());
        JxnuGoNetMethod.getInstance().collectOnePost(collectSubscriber, auth, post);
    }

    @Override
    public void uncollect() {
        uncollectSubsriber = new Subscriber<UncollectStates>() {
            @Override
            public void onCompleted() {
                mPostDetailView.uncollectSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mPostDetailView.uncollectFail();
            }

            @Override
            public void onNext(UncollectStates uncollectStates) {

            }
        };
        UncollectPost post = new UncollectPost();
        post.setUserId(userInfo.getUserId());
        post.setPostId(postId);
        JxnuGoNetMethod.getInstance().uncollectPost(uncollectSubsriber, auth, post);
    }

    @Override
    public void loadPublisherInfo() {
        publisherSubscriber = new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserBasicInfo userBasicInfo) {
                mPostDetailView.insertPublisher(userBasicInfo);
            }
        };
        String str[] = postDetail.getAuthor().split("/");
        int followId = Integer.parseInt(str[str.length - 1]);
        JxnuGoNetMethod.getInstance().getUserInfo(publisherSubscriber, auth, followId);
    }
}
