package market.zy.com.myapplication.db.user;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import market.zy.com.myapplication.entity.user.UserBasicInfo;

/**
 * Created by zpauly on 16-5-19.
 */
public class UserInfoDao {
    public static boolean insertUserInfo(UserBasicInfo info) {
        UserInfoBean userInfo = new UserInfoBean();
        userInfo.setAvatar(info.getAvatar());
        userInfo.setAbout_me(info.getAbout_me());
        userInfo.setContactMe(info.getContactMe());
        userInfo.setFollowed(info.getFollowed());
        userInfo.setFollowers(info.getFollowers());
        userInfo.setLast_seen(info.getLast_seen());
        userInfo.setMember_since(info.getMember_since());
        userInfo.setName(info.getName());
        userInfo.setLocation(info.getLocation());
        userInfo.setPostCollectionCount(info.getPostCollectionCount());
        userInfo.setPostCount(info.getPostCount());
        userInfo.setSex(info.getSex());
        userInfo.setUserId(info.getUserId());
        userInfo.setUserName(info.getUserName());
        return userInfo.save();
    }

    public static int deleteUserInfo() {
        return DataSupport.deleteAll(UserInfoBean.class);
    }

    public static UserInfoBean queryUserInfo() {
        return DataSupport.findFirst(UserInfoBean.class);
    }

    public static int updateUserInfo(ContentValues values) {
        return DataSupport.updateAll(UserInfoBean.class, values);
    }
}
