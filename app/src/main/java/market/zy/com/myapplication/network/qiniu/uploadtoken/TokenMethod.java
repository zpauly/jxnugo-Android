package market.zy.com.myapplication.network.qiniu.uploadtoken;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.qiniu.QiniuUploadToken;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 16-5-7.
 */
public class TokenMethod {
    private static TokenMethod instance = null;

    private Retrofit retrofit;

    private ITokenService service;

    private TokenMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

        service = retrofit.create(ITokenService.class);
    }

    public static TokenMethod getInstance() {
        if (instance == null) {
            synchronized (TokenMethod.class) {
                if (instance == null) {
                    instance = new TokenMethod();
                }
            }
        }
        return instance;
    }

    public void getUploadToken(Observer<QiniuUploadToken> observer) {
        service.getUploadToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
