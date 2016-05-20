package market.zy.com.myapplication.network.login_register;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.login.LoginTokenSuccess;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-17.
 */
public class LoginMethod {
    private static LoginMethod instance;

    private Retrofit retrofit;

    private ILoginService service;

    private LoginMethod() {
        retrofit = RetrofitUtil.initRetrofit(Constants.BASE_URL);

        service = retrofit.create(ILoginService.class);
    }

    public static LoginMethod getInstance() {
        if (instance == null) {
            synchronized (LoginMethod.class) {
                if (instance == null) {
                    instance = new LoginMethod();
                }
            }
        }
        return instance;
    }

    public void login(Observer<LoginTokenSuccess> observer, String auth, String username, String password) {
        service.getLoginToken(auth, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
