package market.zy.com.myapplication.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import market.zy.com.myapplication.Constants;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 16-5-7.
 */
public class RetrofitUtil {
    public static Retrofit initRetrofit(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        return retrofit;
    }

    public static Retrofit initAuthRetrofit(final String baseUrl, String username, String password) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        final String basic = Credentials.basic(username, password);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request authorisedRequest = chain.request().newBuilder()
                        .addHeader("Authorization", basic)
                        .build();
                return chain.proceed(authorisedRequest);
            }
        });
        builder.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        return retrofit;
    }
}
