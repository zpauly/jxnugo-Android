package market.zy.com.myapplication.engine;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.utils.ImageUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-20.
 */
public class ImageEngine {
    public static void loadimageFromUrl(final Context context, final CircleImageView avatar, final String url) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = ImageUtil.getBitmapFromURL(url);
                subscriber.onNext(bitmap);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Glide.with(context)
                                .load(bitmap)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(avatar);
                    }
                });
    }
}
