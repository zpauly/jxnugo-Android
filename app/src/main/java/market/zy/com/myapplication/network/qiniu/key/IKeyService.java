package market.zy.com.myapplication.network.qiniu.key;

import market.zy.com.myapplication.entity.qiniu.QiniuKey;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by root on 16-5-7.
 */
public interface IKeyService {

    @GET("get_mobile_key")
    Observable<QiniuKey> getKey();
}
