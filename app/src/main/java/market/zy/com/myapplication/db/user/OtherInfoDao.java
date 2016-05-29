package market.zy.com.myapplication.db.user;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

import market.zy.com.myapplication.entity.user.UserBasicInfo;

/**
 * Created by zpauly on 16-5-29.
 */
public class OtherInfoDao {
    public static boolean insertOtherInfo(UserBasicInfo info) {
        OtherInfoModel otherInfoBean = new OtherInfoModel();
        otherInfoBean.setAvatar(info.getAvatar());
        otherInfoBean.setAbout_me(info.getAbout_me());
        otherInfoBean.setContactMe(info.getContactMe());
        otherInfoBean.setFollowed(info.getFollowed());
        otherInfoBean.setFollowers(info.getFollowers());
        otherInfoBean.setLast_seen(info.getLast_seen());
        otherInfoBean.setMember_since(info.getMember_since());
        otherInfoBean.setName(info.getName());
        otherInfoBean.setLocation(info.getLocation());
        otherInfoBean.setPostCollectionCount(info.getPostCollectionCount());
        otherInfoBean.setPostCount(info.getPostCount());
        otherInfoBean.setSex(info.getSex());
        otherInfoBean.setUserId(info.getUserId());
        otherInfoBean.setUserName(info.getUserName());
        return otherInfoBean.save();
    }

    public static List<OtherInfoModel> queryOtherInfoByUserId(int userId) {
        return DataSupport.where("userid = ?", String.valueOf(userId)).find(OtherInfoModel.class);
    }

    public static int deleteAll() {
        return DataSupport.deleteAll(OtherInfoModel.class);
    }

    public static int updateAll(ContentValues values) {
        return DataSupport.updateAll(OtherInfoModel.class, values);
    }
}
