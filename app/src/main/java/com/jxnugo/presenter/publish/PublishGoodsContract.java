package com.jxnugo.presenter.publish;

import java.util.List;

import com.jxnugo.base.BasePresenter;
import com.jxnugo.base.BaseView;
import com.jxnugo.entity.post.PhotoKey;
import com.jxnugo.entity.post.publish.NewPost;

/**
 * Created by zpauly on 16-6-6.
 */
public class PublishGoodsContract {
    public interface Presenter extends BasePresenter {
        void uploadImages(String imagePath, int total);

        void uploadOthers(List<PhotoKey> imageKeys);
    }

    public interface View extends BaseView<Presenter> {
        void completeImagesUpload();

        void showUploadFail();

        void showUploadSuccess();

        NewPost createNewPost(List<PhotoKey> imageKeys);
    }
}
