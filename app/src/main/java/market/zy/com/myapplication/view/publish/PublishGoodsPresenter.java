package market.zy.com.myapplication.view.publish;

import android.content.Context;

import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import market.zy.com.myapplication.db.user.UserInfoDao;
import market.zy.com.myapplication.db.user.UserInfoModel;
import market.zy.com.myapplication.entity.post.PhotoKey;
import market.zy.com.myapplication.entity.post.publish.NewPost;
import market.zy.com.myapplication.entity.post.publish.PublishStates;
import market.zy.com.myapplication.entity.qiniu.QiniuUploadToken;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.network.qiniu.upload.OnUploadListener;
import market.zy.com.myapplication.network.qiniu.uploadtoken.TokenMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.SPUtil;
import market.zy.com.myapplication.utils.qiniu.UploadImages;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-6.
 */
public class PublishGoodsPresenter implements PublishGoodsContract.Presenter {
    private PublishGoodsContract.View mPublishGoodsView;
    private Context mContext;

    private JxnuGoNetMethod netMethod;
    private TokenMethod tokenMethod;
    private UserInfoModel userInfo;
    private String auth;

    private Subscriber<QiniuUploadToken> tokenSubscriber;
    private Subscriber<PublishStates> uploadSubscriber;

    private List<PhotoKey> imageKeys = new ArrayList<>();

    public PublishGoodsPresenter(PublishGoodsContract.View view, Context context) {
        mPublishGoodsView = view;
        mContext = context;
        mPublishGoodsView.setPresenter(this);
    }

    @Override
    public void start() {
        netMethod = JxnuGoNetMethod.getInstance();
        tokenMethod = TokenMethod.getInstance();
        userInfo = UserInfoDao.queryUserInfo();
        auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(mContext).getCurrentUsername()
                , SPUtil.getInstance(mContext).getCurrentPassword());
    }

    @Override
    public void stop() {
        if (tokenSubscriber != null) {
            if (tokenSubscriber.isUnsubscribed()) {
                tokenSubscriber.unsubscribe();
            }
        }
        if (uploadSubscriber != null) {
            if (uploadSubscriber.isUnsubscribed()) {
                uploadSubscriber.unsubscribe();
            }
        }
    }

    @Override
    public void uploadImages(final String imagePath, final int total) {
        tokenSubscriber = new Subscriber<QiniuUploadToken>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(QiniuUploadToken qiniuUploadToken) {
                UploadImages.getInstance().uploadImages(imagePath, qiniuUploadToken.getUptoken()
                        , new OnUploadListener() {
                            @Override
                            public void onCompleted(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    PhotoKey photoKey = new PhotoKey();
                                    photoKey.setKey(key);
                                    imageKeys.add(photoKey);
                                    if (imageKeys.size() == total) {
                                        uploadOthers(imageKeys);
                                    }
                                } else {
                                    mPublishGoodsView.completeImagesUpload();
                                    mPublishGoodsView.showUploadFail();
                                }
                            }

                            @Override
                            public void onProcessing(String key, double percent) {

                            }

                            @Override
                            public boolean onCancelled() {
                                return false;
                            }
                        });
            }
        };
        tokenMethod.getUploadToken(tokenSubscriber);
    }

    @Override
    public void uploadOthers(List<PhotoKey> imageKeys) {
        NewPost newPost = mPublishGoodsView.createNewPost(imageKeys);
        uploadSubscriber = new Subscriber<PublishStates>() {
            @Override
            public void onCompleted() {
                mPublishGoodsView.completeImagesUpload();
                mPublishGoodsView.showUploadSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PublishStates publishSuccess) {

            }
        };
        netMethod.publishNewPost(uploadSubscriber, auth, newPost);
    }
}
