package market.zy.com.myapplication.presenter.comments;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.post.comments.NewComment;

/**
 * Created by zpauly on 16-6-7.
 */
public class CommentsContract {
    public interface Presenter extends BasePresenter {
        void putNewComment(NewComment newComment);

        void loadCommentData(int postId);
    }

    public interface View extends BaseView<Presenter> {
        void putNewCommentSuccess();

        void putNewCommentFail();

        void addComments(AllComments allComments);
    }
}
