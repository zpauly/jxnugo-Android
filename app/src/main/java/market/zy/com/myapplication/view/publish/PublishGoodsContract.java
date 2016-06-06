package market.zy.com.myapplication.view.publish;

import java.util.List;

import market.zy.com.myapplication.base.BasePresenter;
import market.zy.com.myapplication.base.BaseView;
import market.zy.com.myapplication.entity.post.PhotoKey;
import market.zy.com.myapplication.entity.post.publish.NewPost;

/**
 * Created by root on 16-6-6.
 */
public class PublishGoodsContract {
    interface Presenter extends BasePresenter {
        void uploadImages(String imagePath, int total);

        void uploadOthers(List<PhotoKey> imageKeys);
    }

    interface View extends BaseView<Presenter> {
        void completeImagesUpload();

        void showUploadFail();

        void showUploadSuccess();

        NewPost createNewPost(List<PhotoKey> imageKeys);
    }
}
