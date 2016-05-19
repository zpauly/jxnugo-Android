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

    private String username;

    private String password;

    private LoginMethod(String username, String password) {
        this.username = username;
        this.password = password;

        retrofit = RetrofitUtil.initAuthRetrofit(Constants.BASE_URL, username, password);

        service = retrofit.create(ILoginService.class);
    }

    public static LoginMethod getInstance(String username, String password) {
        instance = new LoginMethod(username, password);
        return instance;
    }

    public void login(Observer<LoginTokenSuccess> observer) {
        service.getLoginToken(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
