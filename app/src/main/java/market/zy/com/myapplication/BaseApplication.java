package market.zy.com.myapplication;

import android.content.Context;

import org.litepal.LitePalApplication;

import market.zy.com.myapplication.utils.sinaUtils.WeiboShare;

/**
 * Created by dell on 2016/3/9.
 */
public class BaseApplication extends LitePalApplication {
    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
    }

    /**
     * 注册微博分享功能
     */
    public void weiboShare() {
        WeiboShare weiboShare = new WeiboShare(AppContext);
        weiboShare.registeApp();
    }
}
