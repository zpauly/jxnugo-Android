package com.jxnugo.utils;

import okhttp3.Credentials;

/**
 * Created by zpauly on 16-5-20.
 */
public class AuthUtil {
    public static String getAuthFromUsernameAndPassword(String username, String password) {
        String basic = Credentials.basic(username, password);
        return basic;
    }
}
