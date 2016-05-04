package market.zy.com.myapplication.ui.material;


import android.content.DialogInterface;

import com.cocosw.bottomsheet.BottomSheet;

import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;

/**
 * Created by zpauly on 16-3-25.
 */
public class MaterialBottomSheetActivity extends BaseActivity {

    protected void showShareBottomSheet(DialogInterface.OnClickListener listener) {
        new BottomSheet.Builder(this, R.style.BottomSheet_Dialog)
                .title(getString(R.string.share))
                .grid()
                .sheet(R.menu.share_menu)
                .listener(listener)
                .show();
    }
}
