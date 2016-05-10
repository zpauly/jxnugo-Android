package market.zy.com.myapplication.utils.sinaUtils;

import android.content.Context;

import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

import market.zy.com.myapplication.Constants;


/**
 * Created by zpauly on 16-4-1.
 */
public class WeiboShare implements IWeiboHandler {
    private Context mContext;

    //微博分享接口示例
    private static IWeiboShareAPI mWeiboShareAPI = null;

    //是否安装微博客户端
    public static boolean isInstalledWeibo;
    //微博客户端所支持的SDK版本
    public static int supportApiLevel;

    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;

    public WeiboShare(Context context) {
        mContext = context;
        initialize();
    }

    public static IWeiboShareAPI getWeiboAPIInstance() {
        return mWeiboShareAPI;
    }

    private void initialize() {

        if (mWeiboShareAPI != null) {
            synchronized (WeiboShare.class) {
                if (mWeiboShareAPI != null) {
                    // 创建微博 SDK 接口实例
                    mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, Constants.APP_KEY);
                }
            }
        }

        // 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
        isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
    }

    public void registeApp() {
        mWeiboShareAPI.registerApp();
    }
}
