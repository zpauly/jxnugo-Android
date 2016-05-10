package market.zy.com.myapplication.activity;

import android.content.Context;
import android.content.SharedPreferences;
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

/**
 * Created by dell on 2016/3/8.
 */
public class BaseActivity extends AppCompatActivity {
    public static String IS_USING = "isUsing";
    public static String CURRENT_USER = "current_user";

    private boolean isExitConfirm = false;
    private long lastPressTime = 0;
    private boolean onBackTwice = false;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferencesEditor;

    protected String currentUser;

    public void setOnBackTwiceToTrue() {
        onBackTwice = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(IS_USING, MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();
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
        if (sharedPreferences.getString(CURRENT_USER, null) == null) {
            return false;
        } else {
            currentUser = sharedPreferences.getString(CURRENT_USER, null);
            return true;
        }
    }

    protected String getCurrentUser() {
        return currentUser;
    }

    protected void setCurrentUser(String currentUser) {
        preferencesEditor.putString(CURRENT_USER, currentUser);
    }

    protected void exitCurrentUser() {
        preferencesEditor.remove(CURRENT_USER);
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

    public void setUpWindowExitSlideAnimations() {
        Slide slide = (Slide) TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }

    public void setUpWindowEnterSlideAnimations() {
        Slide slide = (Slide) TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.activity_slide);
        getWindow().setEnterTransition(slide);
    }

    public void setupWindowEnterFadeAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }

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
