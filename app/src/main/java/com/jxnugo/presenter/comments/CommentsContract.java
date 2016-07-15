package com.jxnugo.presenter.comments;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;
import com.jxnugo.entity.post.comments.AllComments;
import com.jxnugo.entity.post.comments.NewComment;

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
