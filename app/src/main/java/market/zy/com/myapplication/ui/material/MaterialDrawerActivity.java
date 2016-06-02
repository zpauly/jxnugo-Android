package market.zy.com.myapplication.ui.material;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.base.BaseActivity;
import market.zy.com.myapplication.view.trade.TradeActivity;
import market.zy.com.myapplication.view.user.UserActivity;
import market.zy.com.myapplication.db.user.UserInfoDao;
import market.zy.com.myapplication.utils.ImageUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zpauly on 2016/3/10.
 */
public class MaterialDrawerActivity extends BaseActivity {
    private static AccountHeader mAccountHeader;
    private ProfileDrawerItem mProfileDrawerItem;
    private static Drawer mDrawer;
    private static DrawerBuilder mDrawerBuilder;


    public void initDrawer(final Activity activity , Toolbar mToolbar, final long identifier) {
        mProfileDrawerItem = new ProfileDrawerItem()
                .withName("zhangyu")
                .withEmail("xxxx@xxxx.com")
                .withIcon(R.mipmap.cheese_1);

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.mipmap.header)
                .addProfiles(mProfileDrawerItem)
                .withProfileImagesClickable(true)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Intent intent = new Intent();
                        intent.setClass(activity, UserActivity.class);
                        activity.startActivity(intent);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        mDrawerBuilder = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(mToolbar)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.trade_list).withIcon(GoogleMaterial.Icon.gmd_shopping_cart).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                if (identifier != Constants.TRADEACTIVITY) {
                                    Intent main = new Intent();
                                    main.setClass(activity, TradeActivity.class);
                                    startActivity(main);
                                    activity.finish();
                                }
                                return false;
                            }
                        }),
                        new PrimaryDrawerItem()
                                .withName(R.string.education_assistant).withIcon(GoogleMaterial.Icon.gmd_assistant)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        showSnackbarTipShort(getCurrentFocus(), R.string.developing);
                                        return true;
                                    }
                                }),
                        new PrimaryDrawerItem()
                                .withName(R.string.campus_bbs).withIcon(GoogleMaterial.Icon.gmd_forum)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        showSnackbarTipShort(getCurrentFocus(), R.string.developing);
                                        return true;
                                    }
                                }),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.exit_current_user)
                                .withIcon(GoogleMaterial.Icon.gmd_exit_to_app)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        SPUtil.getInstance(activity).removeCurrentUser();
                                        UserInfoDao.deleteUserInfo();
                                        resetHeader();
                                        return false;
                                    }
                                })
                );
//                .withStickyFooter(R.layout.nav_footer)

        /*mDrawer.getStickyFooter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        mDrawer = mDrawerBuilder.build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetHeader();
    }

    private void resetHeader() {
        if (SPUtil.getInstance(getParent()).getCurrentUsername() == null) {
            ProfileDrawerItem mProfileDrawerItem = new ProfileDrawerItem()
                    .withName(getResources().getString(R.string.please_login))
                    .withIcon(R.mipmap.cheese_1);
            mAccountHeader.clear();
            mAccountHeader.addProfiles(mProfileDrawerItem);
            mDrawerBuilder.withAccountHeader(mAccountHeader);
            return;
        }
        loadAvatar();
    }

    private void loadAvatar() {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap avatar = ImageUtil.getBitmapFromURL(userInfo.getAvatar());
                subscriber.onNext(avatar);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        mDrawerBuilder.withAccountHeader(mAccountHeader);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        ProfileDrawerItem mProfileDrawerItem = new ProfileDrawerItem()
                                .withName(userInfo.getName())
                                .withEmail(userInfo.getContactMe())
                                .withIcon(bitmap);
                        mAccountHeader.clear();
                        mAccountHeader.addProfiles(mProfileDrawerItem);
                    }
                });
    }

    public void addDrawerItems(IDrawerItem... drawerItems) {
        mDrawer.addItems(drawerItems);
    }
}
