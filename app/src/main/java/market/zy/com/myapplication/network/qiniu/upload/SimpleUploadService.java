package market.zy.com.myapplication.network.qiniu.upload;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import java.io.File;

import market.zy.com.myapplication.entity.qiniu.QiniuUploadToken;
import market.zy.com.myapplication.network.qiniu.uploadtoken.TokenMethod;
import rx.Observer;

/**
 * Created by root on 16-5-4.
 */
public class SimpleUploadService implements IFileUploadService {
    private UploadManager uploadManager;

    private String filePath;

    private byte[] data = new byte[1024];

    private boolean hasbytes = false;

    private File file;

    private String key;

    private String token;

    @Override
    public IFileUploadService putData(String filePath) {
        this.filePath = filePath;
        return this;
    }

    @Override
    public IFileUploadService putData(byte[] data) {
        this.data = data;
        hasbytes = true;
        return this;
    }

    @Override
    public IFileUploadService putData(File file) {
        this.file = file;
        return this;
    }

    @Override
    public IFileUploadService putKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public IFileUploadService getUpFromServer() {
        return this;
    }

    @Override
    public void upload(final UpCompletionHandler handler, final UploadOptions options) {
        uploadManager = Upload.getInstance();

        TokenMethod.getInstance().getUploadToken(new Observer<QiniuUploadToken>() {
            @Override
            public void onCompleted() {
                if (filePath != null) {
                    uploadManager.put(filePath, key, token, handler, options);
                }

                if (hasbytes) {
                    uploadManager.put(data, key, token, handler, options);
                }

                if (file != null) {
                    uploadManager.put(file, key, token, handler, options);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(QiniuUploadToken qiniuUploadToken) {
                token = qiniuUploadToken.getUptoken();
            }
        });
    }
}
