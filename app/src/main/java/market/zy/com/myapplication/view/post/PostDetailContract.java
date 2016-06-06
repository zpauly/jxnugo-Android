package market.zy.com.myapplication.view.post;

import java.util.List;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.db.post.PhotoModel;
import market.zy.com.myapplication.entity.post.collection.JudgeCollectStates;
import market.zy.com.myapplication.entity.post.comments.AllComments;
import market.zy.com.myapplication.entity.user.UserBasicInfo;

/**
 * Created by zpauly on 16-6-6.
 */
public class PostDetailContract {
    interface Presenter extends BasePresenter {
        void loadCommentsData();

        void loadPhotosData();

        void isPostCollected();

        void collect();

        void uncollect();

        void loadPublisherInfo();
    }

    interface View extends BaseView<Presenter> {
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
