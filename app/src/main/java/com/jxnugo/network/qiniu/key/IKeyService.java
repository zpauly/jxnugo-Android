package com.jxnugo.network.qiniu.key;

import com.jxnugo.entity.qiniu.QiniuKey;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by root on 16-5-7.
 */
public interface IKeyService {

    @GET("get_mobile_key")
    Observable<QiniuKey> getKey();
}
