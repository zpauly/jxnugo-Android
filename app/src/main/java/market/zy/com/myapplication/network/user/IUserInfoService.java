package market.zy.com.myapplication.network.user;


import market.zy.com.myapplication.entity.user.BasicInfo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zpauly on 16-5-18.
 */
public interface IUserInfoService {
    @GET("user/{id}")
    Observable<BasicInfo> getUserInfo(@Path("id") int id);
}
