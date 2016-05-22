package market.zy.com.myapplication.network.qiniu.upload;

import com.qiniu.android.storage.UploadManager;

import java.io.IOException;

/**
 * Created by zpauly on 16-5-21.
 */
public class SimpleUploadService extends IFileUploadService {
    private static SimpleUploadService instance;

    private SimpleUploadService() throws IOException {
        super();
    }

    public static SimpleUploadService getInstance() {
        if (instance == null) {
            synchronized (SimpleUploadService.class) {
                if (instance == null) {
                    try {
                        instance = new SimpleUploadService();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public UploadManager setUploadManager() throws IOException {
        return Upload.getInstance();
    }
}
