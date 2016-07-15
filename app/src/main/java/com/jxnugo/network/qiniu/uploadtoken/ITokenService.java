package com.jxnugo.network.qiniu.uploadtoken;

import com.jxnugo.entity.qiniu.QiniuUploadToken;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zpauly on 16-5-7.
 */
public interface ITokenService {

    @GET("get_mobile_token")
    Observable<QiniuUploadToken> getUploadToken();
}
