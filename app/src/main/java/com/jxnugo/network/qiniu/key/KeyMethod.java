package com.jxnugo.network.qiniu.key;

import com.jxnugo.Constants;
import com.jxnugo.entity.qiniu.QiniuKey;
import com.jxnugo.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-7.
 */
public class KeyMethod {
    private static KeyMethod instance = null;

    private Retrofit retrofit;

    private IKeyService service;

    private KeyMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

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
