package market.zy.com.myapplication.engine.qiniu;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadOptions;

import market.zy.com.myapplication.network.qiniu.upload.IFileUploadService;
import market.zy.com.myapplication.network.qiniu.upload.SimpleUploadService;

/**
 * Created by root on 16-5-4.
 */
public class UploadImages {
    private static UploadImages uploadImages = null;

    private IFileUploadService uploadService;

    private UploadImages() {
        uploadService = new SimpleUploadService();
    }

    public static UploadImages getInstance() {
        if (uploadImages == null) {
            synchronized (UploadImages.class) {
                if (uploadImages == null) {
                    uploadImages = new UploadImages();
                }
            }
        }
        return uploadImages;
    }

    public void uploadImages(String imagePath, String key,
                             UpCompletionHandler upCompletionHandler, UploadOptions uploadOptions) {
        uploadService.putData(imagePath)
                .putKey(key)
                .upload(upCompletionHandler, uploadOptions);
    }
}
