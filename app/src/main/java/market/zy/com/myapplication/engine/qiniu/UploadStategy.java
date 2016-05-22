package market.zy.com.myapplication.engine.qiniu;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import market.zy.com.myapplication.entity.qiniu.QiniuUploadToken;
import market.zy.com.myapplication.network.qiniu.uploadtoken.TokenMethod;
import rx.Observer;

/**
 * Created by root on 16-5-8.
 */
public class UploadStategy {

    private String key;

    public void uploadImages(Observer<QiniuUploadToken> observer, final Context context, final String path, final MaterialDialog dialog) {
        TokenMethod.getInstance().getUploadToken(observer);
        /*UploadImages.getInstance().uploadImages(path, null, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + response);
                if (info.isOK()) {
                    dialog.dismiss();
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismiss();
                }
            }
        }, null);*/
        /*KeyMethod.getInstance().getKey(new Observer<QiniuKey>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(QiniuKey qiniuKey) {
                key = qiniuKey.getUpload_key();
            }
        });*/
    }
}
