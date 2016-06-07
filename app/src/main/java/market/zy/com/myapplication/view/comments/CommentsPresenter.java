package market.zy.com.myapplication.view.comments;

import android.content.Context;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.db.user.UserInfoDao;
import market.zy.com.myapplication.db.user.UserInfoModel;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.post.comments.NewComment;
import market.zy.com.myapplication.entity.post.comments.NewCommentStates;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-7.
 */
public class CommentsPresenter implements CommentsContract.Presenter {
    private CommentsContract.View mCommentView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;
    private String auth;
    private UserInfoModel userInfo;

    private Subscriber<AllComments> commentsSubscriber;
    private Subscriber<NewCommentStates> newCommentSuccessSubscriber;

    public CommentsPresenter(CommentsContract.View view, Context context) {
        mCommentView = view;
        mContext = context;
        mCommentView.setPresenter(this);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
        userInfo = UserInfoDao.queryUserInfo();
    }

    @Override
    public void stop() {
        if (commentsSubscriber != null) {
            if (commentsSubscriber.isUnsubscribed()) {
                commentsSubscriber.unsubscribe();
            }
        }
        if (newCommentSuccessSubscriber != null) {
            if (newCommentSuccessSubscriber.isUnsubscribed()) {
                newCommentSuccessSubscriber.unsubscribe();
            }
        }
    }

    @Override
    public void putNewComment(NewComment newComment) {
        newCommentSuccessSubscriber = new Subscriber<NewCommentStates>() {
            @Override
            public void onCompleted() {
                mCommentView.putNewCommentSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mCommentView.putNewCommentFail();
            }

            @Override
            public void onNext(NewCommentStates newCommentSuccess) {

            }
        };
        netMethod.addNewComments(newCommentSuccessSubscriber, auth, newComment);
    }

    @Override
    public void loadCommentData(int postId) {
        commentsSubscriber = new Subscriber<AllComments>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllComments allComments) {
                mCommentView.addComments(allComments);
            }
        };
        JxnuGoNetMethod.getInstance().getAllComments(commentsSubscriber, auth, postId);
    }
}
