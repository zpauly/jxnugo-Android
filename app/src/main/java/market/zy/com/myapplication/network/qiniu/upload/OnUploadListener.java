package market.zy.com.myapplication.network.qiniu.upload;

import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

/**
 * Created by zpauly on 16-5-21.
 */
public interface OnUploadListener {
    void onCompleted(String key, ResponseInfo info, JSONObject response);

    void onProcessing(String key, double percent);

    boolean onCancelled();
}
