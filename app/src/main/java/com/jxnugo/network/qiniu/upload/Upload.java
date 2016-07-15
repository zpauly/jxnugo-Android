package com.jxnugo.network.qiniu.upload;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.persistent.FileRecorder;

import java.io.IOException;

/**
 * Created by root on 16-5-4.
 */
public class Upload {
    private static UploadManager uploadManager = null;

    public static com.qiniu.android.storage.UploadManager getInstance() {
        if (uploadManager == null) {
            synchronized (Upload.class) {
                if (uploadManager == null) {
                    try {
                        setUploadManager();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return uploadManager;
    }

    private static void setUploadManager() throws IOException {
        String dirPath = "/data/";
        Recorder recorder = new FileRecorder(dirPath);

        Configuration config = new Configuration.Builder()
                .recorder(recorder)
                .build();

        uploadManager = new UploadManager(config);
    }
}
