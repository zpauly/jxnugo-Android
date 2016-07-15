package com.jxnugo.utils.qiniu;

import com.jxnugo.network.qiniu.upload.IFileUploadService;
import com.jxnugo.network.qiniu.upload.OnUploadListener;
import com.jxnugo.network.qiniu.upload.SimpleUploadService;

/**
 * Created by zpauly on 16-5-4.
 */
public class UploadImages {
    private static UploadImages uploadImages = null;

    private IFileUploadService uploadService;

    private UploadImages() {
        uploadService = SimpleUploadService.getInstance();
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

    public void uploadImages(String path, String token, OnUploadListener listener) {
        uploadService.setOnUploadListener(listener);
        uploadService.putDataByPath(path, null, token);
    }
}
