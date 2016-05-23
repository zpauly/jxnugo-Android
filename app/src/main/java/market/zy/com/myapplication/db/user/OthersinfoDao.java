package market.zy.com.myapplication.db.user;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

import market.zy.com.myapplication.entity.user.UserBasicInfo;

/**
 * Created by zpauly on 16-5-22.
 */
public class OthersinfoDao {
    public static boolean insertUserInfo(UserBasicInfo info) {
        OthersInfoBean othersInfoBean = new OthersInfoBean();
        othersInfoBean.setAvatar(info.getAvatar());
        othersInfoBean.setAbout_me(info.getAbout_me());
        othersInfoBean.setContactMe(info.getContactMe());
        othersInfoBean.setFollowed(info.getFollowed());
        othersInfoBean.setFollowers(info.getFollowers());
        othersInfoBean.setLast_seen(info.getLast_seen());
        othersInfoBean.setMember_since(info.getMember_since());
        othersInfoBean.setName(info.getName());
        othersInfoBean.setLocation(info.getLocation());
        othersInfoBean.setPostCollectionCount(info.getPostCollectionCount());
        othersInfoBean.setPostCount(info.getPostCount());
        othersInfoBean.setSex(info.getSex());
        othersInfoBean.setUserId(info.getUserId());
        othersInfoBean.setUserName(info.getUserName());
        return othersInfoBean.save();
    }

    public static int deleteAllUserInfo() {
        return DataSupport.deleteAll(OthersInfoBean.class);
    }

    public static OthersInfoBean queryAllUserInfo() {
        return DataSupport.findFirst(OthersInfoBean.class);
    }

    public static List<OthersInfoBean> queryUserInfoByUsername(String username) {
        return DataSupport.where("username = ?", username).find(OthersInfoBean.class);
    }

    public static List<OthersInfoBean> queryUserInfoById(int id) {
        return DataSupport.where("userid = ?", String.valueOf(id)).find(OthersInfoBean.class);
    }

    public static int updateUserInfo(ContentValues values) {
        return DataSupport.updateAll(OthersInfoBean.class, values);
    }
}
