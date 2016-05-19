package market.zy.com.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zpauly on 16-5-18.
 */
public class SPUtil {
    public static final String IS_USING = "IS_USING";
    public static final String CURRENT_USERNAME = "CURRENT_USERNAME";
    public static final String CURRENT_PASSWORD = "CURRENT_PASSWORD";
    public static final String CURRENT_USERID = "USERID";
    public static final String CURRENT_USER_AVATAR = "CURRENT_USER_AVATAR";

    private static SPUtil instance;

    private SharedPreferences cuSharedPreferences;
    private SharedPreferences.Editor cuEditor;

    private SPUtil(Context context) {
        cuSharedPreferences = context.getSharedPreferences(IS_USING, Context.MODE_PRIVATE);
        cuEditor = cuSharedPreferences.edit();
    }

    public static SPUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null) {
                    instance = new SPUtil(context);
                }
            }
        }
        return instance;
    }

    public void putCurrentUsername(String currentUser) {
        cuEditor.putString(CURRENT_USERNAME, currentUser);
        cuEditor.commit();
    }

    public String getCurrentUsername() {
        return cuSharedPreferences.getString(CURRENT_USERNAME, null);
    }

    public void putCurrentPassword(String password) {
        cuEditor.putString(CURRENT_PASSWORD, password);
        cuEditor.commit();
    }

    public String getCurrentPassword() {
        return cuSharedPreferences.getString(CURRENT_PASSWORD, null);
    }

    public void putCurrentUserId(int id) {
        cuEditor.putInt(CURRENT_USERID, id);
        cuEditor.commit();
    }

    public int getCurrentUserId() {
        return cuSharedPreferences.getInt(CURRENT_USERID, -1);
    }

    public void putCurrentUserAvatar(String url) {
        cuEditor.putString(CURRENT_USER_AVATAR, url);
        cuEditor.commit();
    }

    public String getCurrentUserAvatar() {
        return cuSharedPreferences.getString(CURRENT_USER_AVATAR, null);
    }

    public void removeCurrentUser() {
        cuEditor.clear();
        cuEditor.commit();
    }
}
