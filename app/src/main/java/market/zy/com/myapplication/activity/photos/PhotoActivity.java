package market.zy.com.myapplication.activity.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.post.PostDetailsActivity;
import market.zy.com.myapplication.activity.trade.TradeActivity;
import market.zy.com.myapplication.adapter.photoAdapter.PhotoAdapter;
import market.zy.com.myapplication.db.post.PhotoModel;
import market.zy.com.myapplication.db.post.PhotosDao;
import market.zy.com.myapplication.ui.support.PhotosViewPager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 16-5-21.
 */
public class PhotoActivity extends AppCompatActivity {
    @Bind(R.id.photos_viewpager)
    protected PhotosViewPager mViewPager;

    @Bind(R.id.photos_close)
    protected ImageView mCloseImage;

    private PhotoAdapter mAdapter;

    private int postId;
    private int currentPhoto;


    private Observable<List<PhotoModel>> observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);

        postId = getIntent().getIntExtra(TradeActivity.POST_ID, -1);
        currentPhoto = getIntent().getIntExtra(PostDetailsActivity.CURRENT_PHOTO, -1);

        loadPhotosData();

        mCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        unsubscribe();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    private void unsubscribe() {
        if (observable != null)
            observable.unsubscribeOn(Schedulers.immediate());
    }

    private void loadPhotosData() {
        observable = Observable.create(new Observable.OnSubscribe<List<PhotoModel>>() {

            @Override
            public void call(Subscriber<? super List<PhotoModel>> subscriber) {
                subscriber.onNext(PhotosDao.queryPhoto(postId));
                subscriber.onCompleted();
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PhotoModel>>() {
                    @Override
                    public void onCompleted() {
                        mViewPager.setAdapter(mAdapter);
                        mViewPager.setCurrentItem(currentPhoto);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<PhotoModel> photoKeys) {
                        mAdapter = new PhotoAdapter(PhotoActivity.this, photoKeys);
                    }
                });
    }
}
