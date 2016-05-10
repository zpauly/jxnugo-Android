package market.zy.com.myapplication.activity.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseBottomSheet;

/**
 * Created by root on 16-5-9.
 */
public class UserBottomSheet extends BaseBottomSheet {
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback;

    private View mView;

    private Toolbar mToolbar;


    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        mView = View.inflate(getContext(), R.layout.personal_bottom_sheet, null);
        dialog.setContentView(mView);

        mToolbar = (Toolbar) mView.findViewById(R.id.personal_bottom_toolbar);
        mToolbar.setTitle(R.string.personal_info);
        mToolbar.setNavigationIcon(R.mipmap.ic_close_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mToolbar.inflateMenu(R.menu.personal_bottom_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.personal_bottom_edit :
                        break;
                }
                return true;
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
    }
}
