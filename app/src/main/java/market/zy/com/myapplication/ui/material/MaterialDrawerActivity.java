package market.zy.com.myapplication.ui.material;

import android.app.Activity;
import android.content.Intent;
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
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.utils.sinaUtils.Constants;
import market.zy.com.myapplication.activity.trade.TradeActivity;
import market.zy.com.myapplication.activity.user.UserActivity;

/**
 * Created by dell on 2016/3/10.
 */
public class MaterialDrawerActivity extends BaseActivity {
    private AccountHeader mAccountHeader;
    private ProfileDrawerItem mProfileDrawerItem;
    private Drawer mDrawer;


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

        mDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(mToolbar)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.trade_list).withIcon(GoogleMaterial.Icon.gmd_label).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
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
                                .withName("item2").withIcon(GoogleMaterial.Icon.gmd_label),
                        new PrimaryDrawerItem()
                                .withName("item3").withIcon(GoogleMaterial.Icon.gmd_label),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem()
                                .withName("subitem").withIcon(GoogleMaterial.Icon.gmd_filter)
                )
                .withStickyFooter(R.layout.nav_footer)
                .build();

        mDrawer.getStickyFooter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void addDrawerItems(IDrawerItem... drawerItems) {
        mDrawer.addItems(drawerItems);
    }
}
