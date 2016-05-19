package market.zy.com.myapplication.network.user;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.utils.RetrofitUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 16-5-18.
 */
public class UserInfoMethod {
    private static UserInfoMethod instance;

    private Retrofit retrofit;

    private IUserInfoService service;

    private String username;

    private String password;

    private UserInfoMethod(String username, String password) {
        this.username = username;
        this.password = password;

        retrofit = RetrofitUtil.initAuthRetrofit(Constants.BASE_URL, username, password);

        service = retrofit.create(IUserInfoService.class);
    }

    public static UserInfoMethod getInstance(String username, String password) {
        instance = new UserInfoMethod(username, password);
        return instance;
    }

    public void getUserInfo(Observer<UserBasicInfo> observer, int userId) {
        service.getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
