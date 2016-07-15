package com.jxnugo.view.user;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import com.jxnugo.R;
import com.jxnugo.view.amend.AmendActivity;
import com.jxnugo.db.user.OtherInfoDao;
import com.jxnugo.db.user.OtherInfoModel;
import com.jxnugo.db.user.UserInfoModel;
import com.jxnugo.db.user.UserInfoDao;

/**
 * Created by root on 16-5-9.
 */
public class UserBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback;

    private View mView;

    @Bind(R.id.personal_bottom_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.personal_bottom_avater)
    protected CircleImageView mAvatar;

    @Bind(R.id.personal_bottom_location)
    protected TextView mLocationText;

    @Bind(R.id.personal_bottom_phone)
    protected TextView mPhoneText;

    @Bind(R.id.personal_bottom_sex)
    protected TextView mSexText;

    @Bind(R.id.personal_bottom_tag)
    protected TextView mTagText;

    private UserInfoModel userInfo;
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        mView = View.inflate(getContext(), R.layout.personal_bottom_sheet, null);
        dialog.setContentView(mView);

        ButterKnife.bind(this, mView);
        mToolbar.setTitle(R.string.personal_info);
        mToolbar.setNavigationIcon(R.mipmap.ic_close_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) mView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN)
                    dismiss();
                switch (newState) {

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        };

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
            bottomSheetBehavior.setPeekHeight(getResources().getDimensionPixelOffset(R.dimen.personal_bottom_peek_height));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if (((UserActivity) getActivity()).getPerson() == UserActivity.OTHERS) {
            loadOtherInfo();
        } else {
            loadUserInfo();
            mToolbar.inflateMenu(R.menu.personal_bottom_toolbar_menu);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.personal_bottom_edit :
                            Intent editIntent = new Intent();
                            editIntent.setClass(getActivity(), AmendActivity.class);
                            startActivity(editIntent);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void loadUserInfo() {
        userInfo = UserInfoDao.queryUserInfo();
        Glide.with(this)
                .load(userInfo.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(mAvatar);
        mLocationText.setText(userInfo.getLocation());
        mPhoneText.setText(userInfo.getContactMe());
        mSexText.setText(userInfo.getSex());
        mTagText.setText(userInfo.getAbout_me());
    }

    private void loadOtherInfo() {
        int otherId = ((UserActivity) getActivity()).getOtherId();
        OtherInfoModel otherInfoModel = OtherInfoDao.queryOtherInfoByUserId(otherId).get(0);
        Glide.with(this)
                .load(otherInfoModel.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(mAvatar);
        mLocationText.setText(otherInfoModel.getLocation());
        mPhoneText.setText(otherInfoModel.getContactMe());
        mSexText.setText(otherInfoModel.getSex());
        mTagText.setText(otherInfoModel.getAbout_me());
    }
}
