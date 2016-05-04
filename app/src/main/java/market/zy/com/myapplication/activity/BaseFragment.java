package market.zy.com.myapplication.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dell on 2016/3/8.
 */
public class BaseFragment extends Fragment {
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
}
