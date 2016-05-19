package market.zy.com.myapplication.ui.material;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.activity.trade.TradeActivity;
import market.zy.com.myapplication.activity.user.UserActivity;
import market.zy.com.myapplication.utils.SPUtil;

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
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.exit_current_user)
                                .withIcon(GoogleMaterial.Icon.gmd_exit_to_app)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        SPUtil.getInstance(activity).removeCurrentUser();
                                        return false;
                                    }
                                })
                )
                .withStickyFooter(R.layout.nav_footer)
                .build();

        mDrawer.getStickyFooter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetHeader();
    }

    private void resetHeader() {
        if (SPUtil.getInstance(getParent()).getCurrentUsername() == null) {
            return;
        }
        mProfileDrawerItem = new ProfileDrawerItem()
                .withName(SPUtil.getInstance(getParent()).getCurrentUsername())
                .withIcon(SPUtil.getInstance(getParent()).getCurrentUserAvatar());
        mAccountHeader.updateProfile(mProfileDrawerItem);
    }

    public void addDrawerItems(IDrawerItem... drawerItems) {
        mDrawer.addItems(drawerItems);
    }
}
