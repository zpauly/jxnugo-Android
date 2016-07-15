package com.jxnugo.presenter.post;

import java.util.List;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;
import com.jxnugo.db.post.PhotoModel;
import com.jxnugo.entity.post.collection.JudgeCollectStates;
import com.jxnugo.entity.post.comments.AllComments;
import com.jxnugo.entity.user.UserBasicInfo;

/**
 * Created by zpauly on 16-6-6.
 */
public class PostDetailContract {
    public interface Presenter extends BasePresenter {
        void loadCommentsData();

        void loadPhotosData();

        void isPostCollected();

        void collect();

        void uncollect();

        void loadPublisherInfo();
    }

    public interface View extends BaseView<Presenter> {
        void swapCommentsData(AllComments allComments);

        void swapPhotosData(List<PhotoModel> photoKeys);

        void judgeCollectStates(JudgeCollectStates judgeCollectStates);

        void collectSuccess();

        void collectFail();

        void uncollectSuccess();

        void uncollectFail();

        void insertPublisher(UserBasicInfo userBasicInfo);
    }
}
