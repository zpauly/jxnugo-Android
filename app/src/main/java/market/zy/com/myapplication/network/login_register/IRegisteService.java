package market.zy.com.myapplication.network.login_register;

import market.zy.com.myapplication.entity.registe.RegisteSuccess;
import market.zy.com.myapplication.entity.registe.RegisterInfo;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zpauly on 16-5-22.
 */
public interface IRegisteService {
    @POST("register")
    Observable<RegisteSuccess> registe(@Body RegisterInfo info);
}
