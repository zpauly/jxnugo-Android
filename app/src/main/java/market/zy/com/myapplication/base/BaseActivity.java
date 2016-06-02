package market.zy.com.myapplication.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Toast;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.db.user.UserInfoModel;
import market.zy.com.myapplication.db.user.UserInfoDao;
import market.zy.com.myapplication.utils.SPUtil;

/**
 * Created by dell on 2016/3/8.
 */
public class BaseActivity extends AppCompatActivity {

    private boolean isExitConfirm = false;
    private long lastPressTime = 0;
    private boolean onBackTwice = false;

    protected SPUtil sp;

    protected UserInfoModel userInfo;

    public void setOnBackTwiceToTrue() {
        onBackTwice = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = SPUtil.getInstance(this);

        loadUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserInfo();
    }

    @Override
    public void onBackPressed() {
        if (onBackTwice) {
            super.onBackPressed();
        } else
            if (canExit()) {
                super.onBackPressed();
        }
    }

    protected boolean isCurrentUsing() {
        if (sp.getCurrentUsername() != null) {
            return true;
        } else {
            return false;
        }
    }

    protected void loadUserInfo() {
        userInfo = UserInfoDao.queryUserInfo();
    }

    protected void exitCurrentUser() {
        sp.removeCurrentUser();
    }

    public void showSnackbarTipShort(View view, int resourceId) {
        Snackbar.make(view, getResources().getString(resourceId), Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackbarTipLong(View view, int resourceId) {
        Snackbar.make(view, getResources().getString(resourceId), Snackbar.LENGTH_LONG).show();
    }

    public void showToastShort(Context context, int resourceId) {
        Toast.makeText(context, getResources().getString(resourceId), Toast.LENGTH_SHORT);
    }

    public void showToastLong(Context context, int resourceId) {
        Toast.makeText(context, getResources().getString(resourceId), Toast.LENGTH_LONG);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUpWindowExitSlideAnimations() {
        Slide slide = (Slide) TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUpWindowEnterSlideAnimations() {
        Slide slide = (Slide) TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.activity_slide);
        getWindow().setEnterTransition(slide);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setupWindowEnterFadeAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setupWindowExitFadeAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setExitTransition(fade);
    }

    private boolean canExit(){
        if(System.currentTimeMillis() - lastPressTime > Constants.exitConfirmTime){
            lastPressTime = System.currentTimeMillis();
            showSnackbarTipShort(getCurrentFocus(), R.string.exit_after_backpressed);
            return false;
        } else {
            return true;
        }
    }
}
