package market.zy.com.myapplication.network.qiniu.upload;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadOptions;

import java.io.File;

/**
 * Created by root on 16-5-4.
 */
public interface IFileUploadService {

    IFileUploadService putData(String filePath);

    IFileUploadService putData(byte[] data);

    IFileUploadService putData(File file);

    IFileUploadService putKey(String key);

    IFileUploadService getUpFromServer();

    void upload(UpCompletionHandler handler, UploadOptions options);
}
