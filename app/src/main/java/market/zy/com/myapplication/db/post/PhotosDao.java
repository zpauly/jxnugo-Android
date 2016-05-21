package market.zy.com.myapplication.db.post;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

import market.zy.com.myapplication.entity.post.PhotoKey;

/**
 * Created by zpauly on 16-5-21.
 */
public class PhotosDao {
    public static boolean insertPhotos(PhotoKey photoKey, int postId) {
        PhotoBean photoBean = new PhotoBean();
        photoBean.setKey(photoKey.getKey());
        photoBean.setPostId(postId);
        return photoBean.save();
    }

    public static List<PhotoBean> queryPhoto(int postId) {
        return DataSupport.where("postid = ?", String.valueOf(postId)).find(PhotoBean.class);
    }

    public static int deletePhotoBean() {
        return DataSupport.deleteAll(PhotoBean.class);
    }

    public static int updatePhotoBean(ContentValues values) {
        return DataSupport.updateAll(PhotoBean.class, values);
    }
}
