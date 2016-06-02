package market.zy.com.myapplication.view.usermanager.registe;

import android.content.Context;
import android.widget.EditText;

import market.zy.com.myapplication.entity.user.registe.RegisteStates;
import rx.Subscriber;

/**
 * Created by zpauly on 16-6-2.
 */
public class RegistePresenter implements RegisteContract.Presenter {
    private RegisteContract.View mRegisteView;
    private Context mContext;

    private Subscriber<RegisteStates> registeSubscriber;

    @Override
    public void registe(EditText usernameEt, EditText passwordEt, EditText emailEt) {
        
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
