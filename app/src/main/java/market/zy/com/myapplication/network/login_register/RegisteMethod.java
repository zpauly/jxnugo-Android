package market.zy.com.myapplication.network.login_register;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.registe.RegisteSuccess;
import market.zy.com.myapplication.entity.registe.RegisterInfo;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-22.
 */
public class RegisteMethod {
    private static RegisteMethod instance;

    private Retrofit retrofit;

    private IRegisteService service;

    private RegisteMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

        service = retrofit.create(IRegisteService.class);
    }

    public static RegisteMethod getInstance() {
        if (instance == null) {
            synchronized (RegisteMethod.class) {
                if (instance == null){
                    instance = new RegisteMethod();
                }
            }
        }
        return instance;
    }

    public void registe(Observer<RegisteSuccess> observer, RegisterInfo info) {
        service.registe(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
