package com.jxnugo.network.qiniu.upload;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by zpauly on 16-5-4.
 */
public abstract class IFileUploadService {
    protected UploadManager uploadManager;

    protected UpCompletionHandler handler;

    protected UploadOptions options;

    protected OnUploadListener onUploadListener;

    public IFileUploadService() throws IOException {
        uploadManager = setUploadManager();
        setCallbacks();
    }

    public void setOnUploadListener(OnUploadListener listener) {
        onUploadListener = listener;
    }

    public void putDataByPath(String path, String key, String token) {
        uploadManager.put(path, key, token, handler, options);
    }

    public void putDataByBytes(byte[] bytes, String key, String token) {
        uploadManager.put(bytes, key, token, handler, options);
    }

    public void putDataByFile(File file, String key, String token) {
        uploadManager.put(file, key, token, handler, options);
    }

    protected void setCallbacks() {
        handler = new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (onUploadListener != null) {
                    onUploadListener.onCompleted(key, info, response);
                }
            }
        };
        options = new UploadOptions(null, null, false
                , new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                if (onUploadListener != null) {
                    onUploadListener.onProcessing(key, percent);
                }
            }
        }, new UpCancellationSignal() {
            @Override
            public boolean isCancelled() {
                if (onUploadListener != null) {
                    return onUploadListener.onCancelled();
                }
                return false;
            }
        });
    }

    public abstract UploadManager setUploadManager() throws IOException;
}
