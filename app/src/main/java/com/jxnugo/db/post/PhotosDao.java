package com.jxnugo.db.post;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

import com.jxnugo.entity.post.PhotoKey;

/**
 * Created by zpauly on 16-5-21.
 */
public class PhotosDao {
    public static boolean insertPhotos(PhotoKey photoKey, int postId) {
        PhotoModel photoBean = new PhotoModel();
        photoBean.setKey(photoKey.getKey());
        photoBean.setPostId(postId);
        return photoBean.save();
    }

    public static List<PhotoModel> queryPhoto(int postId) {
        return DataSupport.where("postid = ?", String.valueOf(postId)).find(PhotoModel.class);
    }

    public static int deletePhotoBean() {
        return DataSupport.deleteAll(PhotoModel.class);
    }

    public static int deletePhotoBean(int postId) {
        return DataSupport.deleteAll(PhotoModel.class, "postid = ?", String.valueOf(postId));
    }

    public static int updatePhotoBean(ContentValues values) {
        return DataSupport.updateAll(PhotoModel.class, values);
    }
}
