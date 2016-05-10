package market.zy.com.myapplication.network.qiniu.key;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.qiniu.QiniuKey;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 16-5-7.
 */
public class KeyMethod {
    private static KeyMethod instance = null;

    private Retrofit retrofit;

    private IKeyService service;

    private KeyMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.QINIU_UPLOAD_TOKEN_BASE_URL);

        service = retrofit.create(IKeyService.class);
    }

    public static KeyMethod getInstance() {
        if (instance == null) {
            synchronized (KeyMethod.class) {
                if (instance == null) {
                    instance = new KeyMethod();
                }
            }
        }
        return instance;
    }

    public void getKey(Observer<QiniuKey> observer) {
        service.getKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
