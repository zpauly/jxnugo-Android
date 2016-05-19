package market.zy.com.myapplication.network.login_register;

import market.zy.com.myapplication.entity.login.LoginTokenSuccess;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zpauly on 16-5-17.
 */
public interface ILoginService {
    @GET("get_token")
    Observable<LoginTokenSuccess> getLoginToken(@Query("userName") String username, @Query("passWord") String password);
}
